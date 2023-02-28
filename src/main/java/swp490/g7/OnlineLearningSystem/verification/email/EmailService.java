package swp490.g7.OnlineLearningSystem.verification.email;

import swp490.g7.OnlineLearningSystem.entities.user.domain.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface EmailService {
    void sendEmails(List<User> users);

    void customEmail(User user, String email, String fromAddress,
                     String senderName, String subject, String content, String verifyCode)
            throws MessagingException, UnsupportedEncodingException;

    void sendEmailFlow(User user, String key) throws MessagingException, UnsupportedEncodingException;
}
