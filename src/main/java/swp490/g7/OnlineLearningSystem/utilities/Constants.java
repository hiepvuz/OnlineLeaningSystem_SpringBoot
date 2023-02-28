package swp490.g7.OnlineLearningSystem.utilities;

public class Constants {

    // Email Constants
    public static final String REGEX_SPECIAL_CHARACTERS = "[\\.\\*\\+\\?\\^\\${}\\(\\)|\\]\\[\\\\]";
    public static final String REGEX_REPLACED_SPECIAL_CHARACTERS = "\\\\$0";
    public static final String FROM_SENDER = "Onlinelearningsystemfpt@gmail.com";
    public static final String SENDER_NAME = "Online Learning System";
    public static final String SUBJECT = "Please verify your registration";
    public static final String CONTENT_VERIFY_ACCOUNT = "Dear [[NAME]],<br>\n" +
            "Please click link below to verify your registration:<br>\n" +
            "<h3><a href=\"[[URL]]\" target=\"_self\">ClickHereToVerify</a></h3>" +
            "Thank you,<br>";
    public static final String REGEX_EMAIL = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String REGEX_SPLIT_NUMBER_ONLY = "([0-9]+)";
    public static final String REGEX_SPLIT_CHARACTER_ONLY = "([a-zA-Z]+)";
    public static final String BLANK_STRING = "";
    public static final String SUBJECT_DEFAULT_PASSWORD = "Change your password";
    public static final String CONTENT_CHANGE_PASSWORD = "Hi [[NAME]],<br>\n" +
            "If you did not make this request then please ignore this email." +
            "A request has been received to change password for your account, please click to link below:<br>\n" +
            "<h3><a href=\"[[URL]]\" target=\"_self\">ChangePassword</a></h3>" +
            "Thank you,<br>";
    public static final String EMAIL_REGISTER_REQUEST = "1";
    public static final String EMAIL_NEW_PASSWORD_REQUEST = "2";

    // Permission
    public static final String DELETE = "DELETE";
    public static final String ALL = "ALL";
    public static final String UPDATE = "UPDATE";
    public static final String ADD = "ADD";

    // Screens Name
    public static final String USER_LIST = "USER_LIST_SCREEN";
    public static final String USER_DETAILS = "USER_DETAILS_SCREEN";
    public static final String SETTING_LIST = "SETTING_LIST_SCREEN";
    public static final String SETTING_DETAILS = "SETTING_DETAILS_SCREEN";
    public static final String CLASS_LIST = "CLASS_LIST_SCREEN";
    public static final String CLASS_DETAILS = "CLASS_DETAILS_SCREEN";
    public static final String SUBJECT_LIST = "SUBJECT_LIST_SCREEN";
    public static final String SUBJECT_DETAILS = "SUBJECT_DETAILS_SCREEN";
    public static final String TRAINEE_LIST = "TRAINEE_LIST_SCREEN";
    public static final String TRAINEE_DETAILS = "TRAINEE_DETAILS_SCREEN";
    public static final String MY_COURSE = "MY_COURSE_SCREEN";
    public static final String CONTACT = "CONTACT_SCREEN";
    public static final String SUBJECT_SETTING_LIST = "SUBJECT_SETTING_LIST_SCREEN";
    public static final String SUBJECT_SETTING_DETAILS = "SUBJECT_SETTING_DETAILS_SCREEN";
    public static final String CLASS_SETTING_LIST = "CLASS_SETTING_LIST_SCREEN";
    public static final String CLASS_SETTING_DETAILS = "CLASS_SETTING_DETAILS_SCREEN";
    public static final String QUESTION_LIST = "QUESTION_LIST_SCREEN";
    public static final String QUESTION_IMPORT = "QUESTION_IMPORT_SCREEN";
    public static final String QUESTION_DETAILS = "QUESTION_DETAILS_SCREEN";
    public static final String TEST_LIST = "TEST_LIST_SCREEN";
    public static final String TEST_DETAILS = "TEST_DETAILS_SCREEN";
    public static final String KEYWORD_LIST = "KEYWORD_LIST_SCREEN";
    public static final String KEYWORD_DETAILS = "KEYWORD_DETAILS_SCREEN";
    public static final String KEYWORD_IMPORT = "KEYWORD_IMPORT_SCREEN";
    public static final String LESSON_LIST = "LESSON_LIST_SCREEN";
    public static final String LESSON_DETAILS = "LESSON_DETAILS_SCREEN";
    public static final String CLASS_LESSON_LIST = "CLASS_LESSON_LIST_SCREEN";
    public static final String CLASS_LESSON_DETAILS = "CLASS_LESSON_DETAILS_SCREEN";
    public static final String SUBJECT_HOME = "SUBJECT_HOME_SCREEN";
    public static final String LEARNING_PROGRESS = "LEARNING_PROGRESS_SCREEN";
    public static final String LESSON_VIEW = "LESSON_VIEW_SCREEN";
    public static final String TEST_PRACTICE_HISTORY = "TEST_PRACTICE_HISTORY_SCREEN";
    public static final String KNOWLEDGE_REVIEW = "KNOWLEDGE_REVIEW_SCREEN";
    public static final String NORMAL_LESSON = "NORMAL_LESSON_SCREEN";
    public static final String QUIZ_LESSON = "QUIZ_LESSON_SCREEN";
    public static final String KEYWORD_VIEW = "KEYWORD_VIEW_SCREEN";
    public static final String TEST_PRACTICE_DETAILS = "TEST_PRACTICE_REVIEW_SCREEN";
    public static final String TEST_HANDLE = "TEST_HANDLE_SCREEN";
    public static final String FLASH_CARD_DETAILS = "FLASH_CARD_DETAILS_SCREEN";
    public static final String FLASH_CARD_VIEW = "FLASH_CARD_VIEW_SCREEN";
    public static final String CODING_LESSON = "CODING_LESSON_SCREEN";
    public static final String CODING_PRACTICE = "CODING_PRACTICE_SCREEN";
    public static final String CODE_PRACTICE_LIST = "CODE_PRACTICE_LIST_SCREEN";
    public static final String CODE_PRACTICE_DETAILS = "CODE_PRACTICE_DETAILS_SCREEN";
    public static final String TEST_CASE_IMPORT = "TEST_CASE_IMPORT_SCREEN";
    public static final String TEST_PRACTICE_RESULT = "TEST_PRACTICE_RESULT_SCREEN";
    public static final String SIMULATION_TEST = "SIMULATION_TEST_SCREEN";
    public static final String TEST_QUESTION_REVIEW = "TEST_QUESTION_REVIEW_SCREEN";
    public static final String WEB_CONTACT = "WEB_CONTACT";

    // File Extension
    public static final String PNG_FORMAT = "png";
    public static final String JPG_FORMAT = "jpg";
    public static final String CSV_FORMAT = "csv";
    public static final String XLSX_FORMAT = "xlsx";
    public static final String ZIP_EXTENSION = ".zip";
}
