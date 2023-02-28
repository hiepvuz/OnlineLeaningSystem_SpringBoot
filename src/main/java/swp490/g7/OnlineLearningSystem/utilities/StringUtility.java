package swp490.g7.OnlineLearningSystem.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtility {
    private static final Logger logger = LogManager.getLogger(StringUtility.class);

    public static String validateSpecialCharacters(String request) {
        Pattern p = Pattern.compile(Constants.REGEX_SPECIAL_CHARACTERS);
        Matcher m = p.matcher(request);
        return m.replaceAll(Constants.REGEX_REPLACED_SPECIAL_CHARACTERS);
    }
}
