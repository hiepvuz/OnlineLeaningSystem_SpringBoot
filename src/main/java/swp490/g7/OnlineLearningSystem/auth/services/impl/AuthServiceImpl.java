package swp490.g7.OnlineLearningSystem.auth.services.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import swp490.g7.OnlineLearningSystem.auth.domain.RefreshToken;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.request.LoginRequest;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.request.NewPasswordRequest;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.request.SignupRequest;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.request.TokenRefreshRequest;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.response.JwtResponse;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.response.MessageResponse;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.response.TokenRefreshResponse;
import swp490.g7.OnlineLearningSystem.auth.services.AuthService;
import swp490.g7.OnlineLearningSystem.auth.services.RefreshTokenService;
import swp490.g7.OnlineLearningSystem.entities.setting.domain.Setting;
import swp490.g7.OnlineLearningSystem.entities.setting.repository.SettingRepository;
import swp490.g7.OnlineLearningSystem.entities.user.domain.User;
import swp490.g7.OnlineLearningSystem.entities.user.repository.UserRepository;
import swp490.g7.OnlineLearningSystem.entities.user.utilities.AutoGenerateUsername;
import swp490.g7.OnlineLearningSystem.errorhandling.CommonErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.errorhandling.ValidationErrorCode;
import swp490.g7.OnlineLearningSystem.security.services.UserDetailsImpl;
import swp490.g7.OnlineLearningSystem.utilities.Constants;
import swp490.g7.OnlineLearningSystem.utilities.JwtUtils;
import swp490.g7.OnlineLearningSystem.verification.email.EmailService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LogManager.getLogger(AuthServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AutoGenerateUsername autoGenerateUsername;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailService emailService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Value("${app.email.host}")
    private String siteURL;

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        String userName = loginRequest.getUsername();
        if (loginRequest.getUsername().matches(Constants.REGEX_EMAIL)) {
            Optional<User> user = Optional.ofNullable(userRepository.findByEmail(loginRequest.getUsername())
                    .orElseThrow(() -> new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, loginRequest.getUsername())));
            userName = user.get().getUsername();
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        //Set logic verify email
        if (!userDetails.getVerify()) {
            logger.error("Current user must be verify!");
            return ResponseEntity.status(401).body(ValidationErrorCode.USER_BE_NOT_VERIFY);
        }

        String jwt = jwtUtils.generateJwtToken(authentication);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        refreshVerifyCode(userDetails.getId());
        return ResponseEntity.ok(new JwtResponse(
                jwt, refreshToken.getToken(),
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getDisable(),
                roles,
                userDetails.getVerify(),
                userDetails.getGoogleAuth()));
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signupRequest) {
        long existRoles = settingRepository.count();
        if (existRoles == 0) {
            logger.error("Role data is empty!");
            throw new OnlineLearningException(ErrorTypes.ROLE_NOT_FOUND, signupRequest.getRoles().toString());
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(ValidationErrorCode.EMAIL_IS_ALREADY_TAKEN));
        }
        String username = autoGenerateUsername.generateUsername(signupRequest.getEmail());

        // Create new user's account
        User user = new User();
        user.setUsername(username);
        user.setFullName(signupRequest.getFullName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setCreatedDate(new Date());
        user.setVerifyCode(RandomString.make(10));

        Set<String> strRoles = signupRequest.getRoles();
        Set<Setting> roles = new HashSet<>();

        if (strRoles == null) {
            Setting traineeRole = settingRepository.findBySettingTitle(Setting.USER_ROLE_TRAINEE);
            roles.add(traineeRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case Setting.USER_ROLE_ADMIN:
                        Setting adminRole = settingRepository.findBySettingTitle(Setting.USER_ROLE_ADMIN);
                        roles.add(adminRole);
                        break;
                    case Setting.USER_ROLE_MANAGER:
                        Setting managerRole = settingRepository.findBySettingTitle(Setting.USER_ROLE_MANAGER);
                        roles.add(managerRole);
                        break;
                    case Setting.USER_ROLE_EXPERT:
                        Setting expertRole = settingRepository.findBySettingTitle(Setting.USER_ROLE_EXPERT);
                        roles.add(expertRole);
                        break;
                    case Setting.USER_ROLE_TRAINER:
                        Setting trainerRole = settingRepository.findBySettingTitle(Setting.USER_ROLE_TRAINER);
                        roles.add(trainerRole);
                        break;
                    default:
                        Setting traineeRole = settingRepository.findBySettingTitle(Setting.USER_ROLE_TRAINEE);
                        roles.add(traineeRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        try {
            emailService.sendEmailFlow(user, Constants.EMAIL_REGISTER_REQUEST);
            logger.info("Email send to verification");
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new OnlineLearningException(ErrorTypes.EMAIL_SEND_FAILED);
        }
        return ResponseEntity.ok(new JwtResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getDisabled()
        ));
    }

    @Override
    public ResponseEntity<?> logout() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    @Override
    public void sendVerificationEmail(String email)
            throws MessagingException, UnsupportedEncodingException {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            logger.error("User not found with email: {}", email);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, email);
        }
        emailService.sendEmailFlow(user.get(), Constants.EMAIL_REGISTER_REQUEST);
    }

    @Override
    public ResponseEntity<?> verifyEmail(String email, String code) {
        Optional<User> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            logger.error("User not found with email: {}", email);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, email);
        }

        if (!code.equals(user.get().getVerifyCode())) {
            logger.error("Verify code incorrect: {}", code);
            throw new OnlineLearningException(ErrorTypes.VERIFY_CODE_INCORRECT, email);
        }
        user.get().setDisabled(Boolean.FALSE);
        user.get().setVerify(Boolean.TRUE);
        userRepository.save(user.get());

        return ResponseEntity.ok(new JwtResponse(
                user.get().getUserId(),
                user.get().getUsername(),
                user.get().getEmail(),
                user.get().getDisabled()
        ));
    }

    @Override
    public ResponseEntity<?> saveUserByGoogleToken(String token) {
        GoogleCredential credential = new GoogleCredential().setAccessToken(token);
        Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new GsonFactory(), credential)
                .setApplicationName("Oauth2").build();
        Userinfoplus userinfoplus = null;
        try {
            userinfoplus = oauth2.userinfo().get().execute();
            logger.info(userinfoplus.toPrettyString());
        } catch (IOException e) {
            logger.error("Failed to get user info by google token! ");
            throw new OnlineLearningException(ErrorTypes.INVALID_GOOGLE_AUTHENTICATION);
        }

        Optional<User> existUser = userRepository.findByEmail(userinfoplus.getEmail());
        if (!existUser.isPresent()) {
            User user = new User();
            user.setEmail(userinfoplus.getEmail());
            user.setGoogleAuth(Boolean.FALSE);
            user.setFullName(userinfoplus.getName());
            user.setVerify(userinfoplus.getVerifiedEmail());
            user.setUsername(autoGenerateUsername.generateUsername(userinfoplus.getEmail()));
            user.setVerifyCode(RandomString.make(10));
            user.setGoogleAuth(Boolean.FALSE);
            user.setDisabled(Boolean.FALSE);
            Setting traineeRole = settingRepository.findBySettingTitle(Setting.USER_ROLE_TRAINEE);
            Set<Setting> roles = new HashSet<>();
            roles.add(traineeRole);
            user.setRoles(roles);
            userRepository.save(user);
            return googleAccessHandler(token, user, user.getRoles());
        }
        return googleAccessHandler(token, existUser.get(), existUser.get().getRoles());
    }

    private ResponseEntity<?> googleAccessHandler(String jwt, User user, Set<Setting> roles) {
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserId());
        List<String> roleNames = roles.stream()
                .map(Setting::getSettingTitle)
                .collect(Collectors.toList());
        refreshVerifyCode(user.getUserId());
        return ResponseEntity.ok(new JwtResponse(
                jwt, refreshToken.getToken(),
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getDisabled(),
                roleNames,
                user.getVerify(),
                user.getGoogleAuth()));
    }

    private void refreshVerifyCode(Long id) {
        logger.info("Refresh verify code user with user id: {}", id);
        Optional<User> user = userRepository.findById(id);
        user.get().setVerifyCode(RandomString.make(10));
        userRepository.save(user.get());
    }

    @Override
    public void changePasswordRequest(String email)
            throws MessagingException, UnsupportedEncodingException {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            logger.error("User not found with email: {}", email);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, email);
        }
        String newPassword = RandomString.make(8);
        user.get().setPassword(encoder.encode(newPassword));
        user.get().setGoogleAuth(Boolean.TRUE);
        userRepository.save(user.get());
        emailService.sendEmailFlow(user.get(), Constants.EMAIL_NEW_PASSWORD_REQUEST);
    }

    @Override
    public void newPasswordRequest(NewPasswordRequest request) {
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Invalid request");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        logger.info("Start set new password for user with email: {}", request.getEmail());
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (!user.isPresent()) {
            logger.error("User not found with email: {}", request.getEmail());
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND, request.getEmail());
        }
        if (!request.getVerifyCode().equals(user.get().getVerifyCode())) {
            logger.error("Verify code incorrect: {}", request.getVerifyCode());
            throw new OnlineLearningException(ErrorTypes.VERIFY_CODE_INCORRECT, request.getEmail());
        }
        user.get().setDisabled(Boolean.FALSE);
        user.get().setVerify(Boolean.TRUE);
        user.get().setPassword(encoder.encode(request.getNewPassword()));
        userRepository.save(user.get());
        logger.info("Successfully set new password for user with email: {}", request.getEmail());
    }

    @Override
    public ResponseEntity<?> adminRegisterUser(SignupRequest signUpRequest) {
        long existRoles = settingRepository.count();
        if (existRoles == 0) {
            logger.error("Role data is empty!");
            throw new OnlineLearningException(ErrorTypes.ROLE_NOT_FOUND, signUpRequest.getRoles().toString());
        }

        String username = autoGenerateUsername.generateUsername(signUpRequest.getEmail());

        // Create new user's account
        User user = new User();
        user.setUsername(username);
        user.setFullName(signUpRequest.getFullName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setCreatedDate(new Date());
        user.setVerifyCode(RandomString.make(10));
        user.setVerify(true);
        user.setDisabled(false);

        Set<Setting> roles = new HashSet<>();

        Setting traineeRole = settingRepository.findBySettingTitle(Setting.USER_ROLE_TRAINEE);
        roles.add(traineeRole);

        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new JwtResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getDisabled()
        ));
    }

    @Override
    public ResponseEntity<?> refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new OnlineLearningException(CommonErrorTypes.REFRESH_TOKEN_FAILED));
    }
}
