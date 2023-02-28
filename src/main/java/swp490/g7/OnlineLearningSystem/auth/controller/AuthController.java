package swp490.g7.OnlineLearningSystem.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.request.LoginRequest;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.request.NewPasswordRequest;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.request.SignupRequest;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.request.TokenRefreshRequest;
import swp490.g7.OnlineLearningSystem.auth.services.AuthService;
import swp490.g7.OnlineLearningSystem.entities.user.services.UserService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> logoutUser() {
        return authService.logout();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        return authService.refreshToken(request);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam("email") String email, @RequestParam("code") String code) {
        return authService.verifyEmail(email, code);
    }

    @PutMapping("/forgot-password")
    public void sendRequestChangePassword(@RequestParam("email") String email) throws MessagingException, UnsupportedEncodingException {
        authService.changePasswordRequest(email);
    }

    @PutMapping("/new-password")
    public void newPassword(@RequestParam("email") String email, @RequestParam("newPassword") String newPassword) {
        userService.newPassword(email, newPassword);
    }

    @GetMapping("/google-access")
    public ResponseEntity<?> getGoogleToken(@RequestParam("token") String token) {
        return authService.saveUserByGoogleToken(token);
    }

    @GetMapping("/verify-request")
    public void verifyRequest(@RequestParam("email") String email) throws MessagingException, UnsupportedEncodingException {
        authService.sendVerificationEmail(email);
    }

    @PutMapping("password-request")
    public void passwordDefault(@RequestParam("email") String email) throws MessagingException, UnsupportedEncodingException {
        authService.changePasswordRequest(email);
    }

    @PutMapping("new-password-request")
    public void newPasswordRequest(@Valid @RequestBody NewPasswordRequest request) {
        authService.newPasswordRequest(request);
    }
}
