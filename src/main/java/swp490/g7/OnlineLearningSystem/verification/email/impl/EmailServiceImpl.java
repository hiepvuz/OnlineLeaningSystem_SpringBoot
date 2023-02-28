package swp490.g7.OnlineLearningSystem.verification.email.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import swp490.g7.OnlineLearningSystem.entities.user.domain.User;
import swp490.g7.OnlineLearningSystem.utilities.Constants;
import swp490.g7.OnlineLearningSystem.verification.email.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EmailServiceImpl implements EmailService {
    private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);

    @Value("${app.email.host}")
    private String siteURL;

    public static final String NAME_EMAIL = "[[NAME]]";

    public static final String URL = "[[URL]]";

    public static final String PATH_REQUEST = "/verify";

    public static final String EMAIL_PARAM = "?email=";

    public static final String CODE_PARAM = "&code=";

    public static final String CASE_PARAM = "&case=";

    @Autowired
    private JavaMailSender mailSender;

    @Async
    @Override
    public void sendEmails(List<User> users) {
        CompletableFuture.runAsync(() -> users.stream().forEach(u -> {
            try {
                sendEmailFlow(u, Constants.EMAIL_NEW_PASSWORD_REQUEST);
            } catch (MessagingException e) {
                logger.error("Email sending failed!", u.getEmail());
            } catch (UnsupportedEncodingException e) {
                logger.error("Email sending failed!", u.getEmail());
            }
        }));
    }

    @Override
    public void customEmail(User user, String email, String fromAddress,
                            String senderName, String subject, String content, String verifyCode)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(email);
        helper.setSubject(subject);
        content = content.replace(NAME_EMAIL, user.getUsername());
        content = content.replace(URL, verifyCode);
        content = content.replace("[[CODE]]", verifyCode);
        helper.setText(content, Boolean.TRUE);
        mailSender.send(message);
        logger.info("Email send successfully");
    }

    @Override
    public void sendEmailFlow(User user, String key)
            throws MessagingException, UnsupportedEncodingException {
        String fromAddress = Constants.FROM_SENDER;
        String senderName = Constants.SENDER_NAME;
        String subject = Constants.SUBJECT;
        String content;
        switch (key) {
            case Constants.EMAIL_REGISTER_REQUEST:
                content = Constants.CONTENT_VERIFY_ACCOUNT;
                break;
            default:
                content = Constants.CONTENT_CHANGE_PASSWORD;
        }
        content += Constants.SENDER_NAME;
        String verifyURL = siteURL + PATH_REQUEST
                + EMAIL_PARAM + user.getEmail()
                + CODE_PARAM + user.getVerifyCode()
                + CASE_PARAM + key;
        customEmail(user, user.getEmail(), fromAddress, senderName, subject, content, verifyURL);
    }
}
