//package swp490.g7.OnlineLearningSystem.service.authorization;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import swp490.g7.OnlineLearningSystem.auth.domain.RefreshToken;
//import swp490.g7.OnlineLearningSystem.auth.domain.payload.request.LoginRequest;
//import swp490.g7.OnlineLearningSystem.auth.services.RefreshTokenService;
//import swp490.g7.OnlineLearningSystem.auth.services.impl.AuthServiceImpl;
//import swp490.g7.OnlineLearningSystem.entities.setting.domain.Setting;
//import swp490.g7.OnlineLearningSystem.entities.user.domain.User;
//import swp490.g7.OnlineLearningSystem.entities.user.repository.UserRepository;
//import swp490.g7.OnlineLearningSystem.entities.user.services.UserService;
//import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
//import swp490.g7.OnlineLearningSystem.security.services.UserDetailsImpl;
//import swp490.g7.OnlineLearningSystem.utilities.JwtUtils;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//@ExtendWith(MockitoExtension.class)
//public class AuthorizationServiceTest {
//    @Mock
//    private UserService userService;
//    @Mock
//    private JwtUtils jwtUtils;
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private RefreshTokenService refreshTokenService;
//    @Mock
//    private AuthenticationManager authenticationManager;
//    @InjectMocks
//    private AuthServiceImpl authService;
//    private static List<User> users;
//    private static LoginRequest buildLoginUsername;
//    private static LoginRequest buildLoginEmail;
//    private static RefreshToken refreshToken;
//
//    @BeforeEach
//    private void setup() {
//        users = buildUser();
//        buildLoginUsername = buildLoginUsername();
//        buildLoginEmail = buildLoginEmail();
//        refreshToken = buildRefreshToken();
//    }
//
//    private static LoginRequest buildLoginUsername() {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("admin");
//        loginRequest.setPassword("123");
//        return loginRequest;
//    }
//
//    private static LoginRequest buildLoginEmail() {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("admin@gmail.com");
//        loginRequest.setPassword("123");
//        return loginRequest;
//    }
//
//    private static RefreshToken buildRefreshToken() {
//        RefreshToken refreshToken = new RefreshToken();
//        refreshToken.setId(1L);
//        refreshToken.setToken("a12nsv4523nn23bgw");
//        refreshToken.setUser(users.get(0));
//        return refreshToken;
//    }
//
//    private static List<User> buildUser() {
//        User user1 = new User();
//        user1.setUserId(1L);
//        user1.setDisabled(Boolean.FALSE);
//        user1.setUsername("admin");
//        user1.setEmail("admin@gmail.com");
//        user1.setVerify(Boolean.TRUE);
//        user1.setPassword("123");
//        Set<Setting> settings = new HashSet<>();
//        Setting setting1 = new Setting();
//        setting1.setSettingId(1L);
//        setting1.setSettingTitle("Trainee");
//        setting1.setTypeId(5L);
//        settings.add(setting1);
//        user1.setRoles(settings);
//        User user2 = new User();
//        user2.setUserId(2L);
//        user2.setDisabled(Boolean.FALSE);
//        user2.setUsername("trainee");
//        user2.setEmail("trainee@gmail.com");
//        user2.setVerify(Boolean.TRUE);
//        user2.setPassword("123");
//        Set<Setting> settings2 = new HashSet<>();
//        Setting setting2 = new Setting();
//        setting2.setSettingId(2L);
//        setting2.setSettingTitle("Trainee");
//        setting2.setTypeId(5L);
//        settings2.add(setting2);
//        List<User> users = new ArrayList<>();
//        users.add(user1);
//        users.add(user2);
//        return users;
//    }
//
//    @org.junit.jupiter.api.Test
//    public void authenticateUser_successWithEmail() {
//        Authentication authentication = Mockito.mock(Authentication.class);
//        UserDetailsImpl userDetails = UserDetailsImpl.build(users.get(0));
//        Mockito.lenient().when(authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken("admin", buildLoginEmail.getPassword()))).thenReturn(authentication);
//        Mockito.lenient().when(authentication.getPrincipal()).thenReturn(userDetails);
//        Mockito.lenient().when(userRepository.findByEmail(buildLoginEmail.getUsername())).thenReturn(Optional.of(users.get(0)));
//        Mockito.lenient().when(jwtUtils.generateJwtToken(authentication)).thenReturn("axb71h123tr1bsq");
//        Mockito.lenient().when(refreshTokenService.createRefreshToken(userDetails.getId())).thenReturn(refreshToken);
//        Mockito.lenient().when(userRepository.findById(1L)).thenReturn(Optional.of(users.get(0)));
//        ResponseEntity<?> responseEntity = authService.authenticateUser(buildLoginEmail);
//        Assertions.assertNotNull(responseEntity);
//    }
//
//    @org.junit.jupiter.api.Test
//    public void authenticateUser_successWithUsername() {
//        String userName = buildLoginUsername.getUsername();
//        Authentication authentication = Mockito.mock(Authentication.class);
//        UserDetailsImpl userDetails = UserDetailsImpl.build(users.get(0));
//        Mockito.lenient().when(authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(userName, buildLoginUsername.getPassword()))).thenReturn(authentication);
//        Mockito.lenient().when(authentication.getPrincipal()).thenReturn(userDetails);
//        Mockito.lenient().when(userRepository.findByEmail(buildLoginUsername.getUsername())).thenReturn(Optional.of(users.get(0)));
//        Mockito.lenient().when(jwtUtils.generateJwtToken(authentication)).thenReturn("axb71h123tr1bsq");
//        Mockito.lenient().when(refreshTokenService.createRefreshToken(userDetails.getId())).thenReturn(refreshToken);
//        Mockito.lenient().when(userRepository.findById(1L)).thenReturn(Optional.of(users.get(0)));
//        ResponseEntity<?> responseEntity = authService.authenticateUser(buildLoginUsername);
//        Assertions.assertNotNull(responseEntity);
//    }
//
//    @org.junit.jupiter.api.Test
//    public void authenticateUser_failedWithEmail() {
//        Authentication authentication = Mockito.mock(Authentication.class);
//        UserDetailsImpl userDetails = UserDetailsImpl.build(users.get(0));
//        Mockito.lenient().when(authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken("admin", buildLoginEmail.getPassword()))).thenReturn(authentication);
//        Mockito.lenient().when(authentication.getPrincipal()).thenReturn(userDetails);
//        Mockito.lenient().when(userRepository.findByEmail(buildLoginEmail.getUsername())).thenReturn(Optional.empty());
//        Mockito.lenient().when(jwtUtils.generateJwtToken(authentication)).thenReturn("axb71h123tr1bsq");
//        Mockito.lenient().when(refreshTokenService.createRefreshToken(userDetails.getId())).thenReturn(refreshToken);
//        Mockito.lenient().when(userRepository.findById(1L)).thenReturn(Optional.of(users.get(0)));
//        Assertions.assertThrows(OnlineLearningException.class, () -> {
//            authService.authenticateUser(buildLoginEmail);
//        });
//    }
//
//    @org.junit.jupiter.api.Test
//    public void authenticateUser_failedWithUser() {
//        String userName = buildLoginUsername.getUsername();
//        Authentication authentication = Mockito.mock(Authentication.class);
//        UserDetailsImpl userDetails = UserDetailsImpl.build(users.get(0));
//        Mockito.lenient().when(authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(userName, buildLoginEmail.getPassword()))).thenReturn(null);
//        Mockito.lenient().when(authentication.getPrincipal()).thenReturn(userDetails);
//        Mockito.lenient().when(userRepository.findByEmail(buildLoginEmail.getUsername())).thenReturn(Optional.empty());
//        Mockito.lenient().when(jwtUtils.generateJwtToken(authentication)).thenReturn("axb71h123tr1bsq");
//        Mockito.lenient().when(refreshTokenService.createRefreshToken(userDetails.getId())).thenReturn(refreshToken);
//        Mockito.lenient().when(userRepository.findById(1L)).thenReturn(Optional.of(users.get(0)));
//        Assertions.assertThrows(OnlineLearningException.class, () -> {
//            authService.authenticateUser(buildLoginEmail);
//        });
//    }
//
//    @org.junit.jupiter.api.Test
//    public void authenticateUser_failedWrongPassword() {
//        Authentication authentication = Mockito.mock(Authentication.class);
//        UserDetailsImpl userDetails = UserDetailsImpl.build(users.get(0));
//        Mockito.lenient().when(authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken("admin", buildLoginEmail.getPassword()))).thenReturn(authentication);
//        Mockito.lenient().when(authentication.getPrincipal()).thenReturn(userDetails);
//        Mockito.lenient().when(userRepository.findByEmail(buildLoginEmail.getUsername())).thenReturn(Optional.of(users.get(0)));
//        Mockito.lenient().when(jwtUtils.generateJwtToken(authentication)).thenReturn("axb71h123tr1bsq");
//        Mockito.lenient().when(refreshTokenService.createRefreshToken(userDetails.getId())).thenReturn(refreshToken);
//        Mockito.lenient().when(userRepository.findById(1L)).thenReturn(Optional.of(users.get(0)));
//        ResponseEntity<?> responseEntity = authService.authenticateUser(buildLoginUsername);
//        Assertions.assertNotNull(responseEntity);
//    }
//}