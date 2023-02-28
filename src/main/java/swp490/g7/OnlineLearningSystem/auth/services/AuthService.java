package swp490.g7.OnlineLearningSystem.auth.services;

import org.springframework.http.ResponseEntity;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.request.LoginRequest;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.request.NewPasswordRequest;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.request.SignupRequest;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.request.TokenRefreshRequest;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface AuthService {

    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);

    ResponseEntity<?> registerUser(SignupRequest signupRequest);

    ResponseEntity<?> logout();

    void sendVerificationEmail(String email) throws MessagingException, UnsupportedEncodingException;

    ResponseEntity<?> verifyEmail(String email, String code);

    ResponseEntity<?> saveUserByGoogleToken(String token);

    void changePasswordRequest(String email) throws MessagingException, UnsupportedEncodingException;

    void newPasswordRequest(NewPasswordRequest request);

    ResponseEntity<?> adminRegisterUser(SignupRequest signUpRequest);

    ResponseEntity<?> refreshToken(TokenRefreshRequest request);
}

