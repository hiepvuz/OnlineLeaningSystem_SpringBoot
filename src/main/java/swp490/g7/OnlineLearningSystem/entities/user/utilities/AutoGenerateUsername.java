package swp490.g7.OnlineLearningSystem.entities.user.utilities;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import swp490.g7.OnlineLearningSystem.entities.user.repository.UserRepository;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AutoGenerateUsername {
    private static final Logger logger = LogManager.getLogger(AutoGenerateUsername.class);

    @Autowired
    private UserRepository userRepository;

    public String generateUsername(String email) {
        String[] splitDomain = email.split("@");

        String number = matchNumberOnly(splitDomain[0]);
        String name = matchCharacterOnly(splitDomain[0]);

        if (StringUtils.isEmpty(number) || StringUtils.isBlank(number)) {
            number = "0";
        }
        number = parseIntAutoIncrement(number) + Constants.BLANK_STRING;
        return checkExistUsernameGenerated(name, number);
    }

    private String matchNumberOnly(String input) {
        Pattern p = Pattern.compile(Constants.REGEX_SPLIT_NUMBER_ONLY);
        Matcher m = p.matcher(input);
        String number = "";
        while (m.find()) {
            number = m.group();
        }
        return number;
    }

    private String matchCharacterOnly(String input) {
        Pattern p = Pattern.compile(Constants.REGEX_SPLIT_CHARACTER_ONLY);
        Matcher m = p.matcher(input);
        String name = "";
        while (m.find()) {
            name = m.group();
        }
        return name;
    }

    private int parseIntAutoIncrement(String input) {
        int count = 0;
        try {
            count = Integer.parseInt(input);
            count += 1;
        } catch (Exception e) {
            logger.error("Failed parse number {} from email", input);
            throw new OnlineLearningException(ErrorTypes.FAILED_TO_GET_NUMBER_FROM_EMAIL, input);
        }
        return count;
    }

    private String checkExistUsernameGenerated(String name, String number) {
        String username = name + number;
        while (userRepository.existsByUsername(username)) {
            number = parseIntAutoIncrement(number) + Constants.BLANK_STRING;
            username = name + number;
        }
        return username;
    }
}
