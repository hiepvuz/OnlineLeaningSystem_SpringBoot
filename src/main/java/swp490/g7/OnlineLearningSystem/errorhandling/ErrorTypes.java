package swp490.g7.OnlineLearningSystem.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public enum ErrorTypes implements ErrorType {
    INVALID_REQUEST("INVALID_REQUEST", "Invalid request", 400),
    USER_NOT_FOUND("USER_NOT_FOUND", "User not found.", 404),
    USER_WITH_NAME_ALREADY_EXISTS("USER_WITH_NAME_ALREADY_EXISTS",
            "User with name already exists", 400),
    ROLE_WITH_NAME_ALREADY_EXISTS("ROLE_WITH_NAME_ALREADY_EXISTS",
            "User with name already exists", 400),
    PASSWORD_IS_INCORRECT("PASSWORD_IS_INCORRECT", "Password is incorrect", 400),
    ROLE_NOT_FOUND("ROLE_NOT_FOUND", "Role not found", 404),
    CURRENT_USER_NOT_FOUND("CURRENT_USER_NOT_FOUND", "Current user not found", 404),
    SETTING_ID_MUST_NOT_BE_EMPTY("SETTING_ID_MUST_NOT_BE_EMPTY", "Setting id must not be empty", 400),
    TYPE_ID_MUST_NOT_BE_EMPTY("TYPE_ID_MUST_NOT_BE_EMPTY", "Type id must not be empty", 400),
    SETTING_ALREADY_EXIST("SETTING_ALREADY_EXIST", "Setting already exist", 400),
    SETTING_NOT_FOUND("SETTING_NOT_FOUND", "Setting not found", 404),
    DATE_IS_NOT_FORMAT("DATE_IS_NOT_FORMAT", "Date is not format to parse", 400),
    EMAIL_SEND_FAILED("EMAIL_SEND_FAILED", "Email send failed", 404),
    VERIFY_CODE_INCORRECT("VERIFY_CODE_INCORRECT", "Verify code incorrect", 400),
    STATUS_INVALID("STATUS_INVALID", "Status invalid", 400),
    CLASS_NOT_FOUND("CLASS_NOT_FOUND", "Class not found", 404),
    WEB_CONTACT_NOT_FOUND("WEB_CONTACT_NOT_FOUND", "Web contact not found", 404),
    FAILED_TO_GET_NUMBER_FROM_EMAIL("FAILED_TO_GET_NUMBER_FROM_EMAIL", "Failed to get number from email", 400),
    INVALID_GOOGLE_AUTHENTICATION("INVALID_GOOGLE_AUTHENTICATION", "Invalid google authentication", 400),
    TRAINEE_NOT_EXISTS("TRAINEE NOT EXISTS", "Trainee not exists", 404),
    ROLE_HAVE_NOT_PERMISSION("ROLE_HAVE_NOT_PERMISSION",
            "current user have not permission to use this function", 400),
    SUBJECT_LIST_NOT_FOUND("SUBJECT_LIST_NOT_FOUND", "can not load list subject", 404),
    SUBJECT_WITH_CODE_ALREADY_EXISTS("SUBJECT_WITH_CODE_ALREADY_EXISTS",
            "subject with name already exists", 400),
    SUBJECT_WITH_CODE_NOT_FOUND("SUBJECT_WITH_CODE_NOT_FOUND",
            "subject with code not found", 404),
    SUBJECT_WITH_ID_NOT_FOUND("SUBJECT_WITH_ID_NOT_EXIST",
            "subject with id not exist", 404),
    MANAGER_WITH_ID_NOT_EXIST("MANAGER_WITH_ID_NOT_EXIST",
            "manager with id not exist", 404),
    EXPERT_WITH_ID_NOT_EXIST("EXPERT_WITH_ID_NOT_EXIST",
            "expert with id not exist", 404),
    REQUEST_MUST_NOT_BE_EMPTY("REQUEST_MUST_NOT_BE_EMPTY", "Request must not be empty", 400),
    PERMISSION_NOT_FOUND("PERMISSION NOT FOUND", "Permission not found", 404),
    PERMISSION_ALREADY_EXIST("PERMISSION_ALREADY_EXIST", "Permission already exist", 400),
    ACCESS_DENIED("ACCESS_DENIED", "Access Denied", 403),
    PERMISSION_NOT_DEFINE("PERMISSION_NOT_DEFINE", "Permission not define", 403),
    USER_ID_MUST_BE_CURRENT_USER("USER_ID_MUST_BE_CURRENT_USER", "User id must be current user!", 400),
    USER_ROLE_WITH_DISPLAY_ORDER_NOT_FOUND("USER_ROLE_NOT_FOUND_BY_DISPLAY_ORDER",
            "Role id with display order is not exist", 400),
    QUESTION_NOT_FOUND("QUESTION_NOT_FOUND", "Question not found", 404),
    CONTENT_GROUP_NOT_FOUND("CONTENT_GROUP_NOT_FOUND", "Content Group not found", 404),
    SUBJECT_SETTING_NOT_FOUND("SUBJECT_SETTING_NOT_FOUND", "Subject Setting not found", 404),
    CLASS_SETTING_NOT_FOUND("CLASS_SETTING_NOT_FOUND", "Class Setting not found", 404),

    DOWNLOAD_QUESTION_TEMPLATE_FAILED("DOWNLOAD_QUESTION_TEMPLATE_FAILED",
            "Download question template failed", 400),
    ANSWER_OPTION_IS_EMPTY("ANSWER_OPTION_IS_EMPTY", "Answer option is empty", 404),
    AMAZON_SERVICE_EXCEPTION("AMAZON_SERVICE_EXCEPTION", "Amazon service exception", 500),
    FILE_UPLOAD_FAILED("FILE_UPLOAD_FAILED", "File upload failed", 500),
    KEYWORD_NOT_FOUND("KEYWORD_NOT_FOUND", "Keyword not found", 404),
    EMPTY_FOLDER("EMPTY_FOLDER", "Empty folder", 404),
    IMAGE_NAME_NOT_MATCH_WITH_ANY_IMAGE("IMAGE_NAME_NOT_MATCH_WITH_ANY_IMAGE",
            "Image name not match with any image", 400),
    QUESTION_WITH_TEST_EXISTED("QUESTION_WITH_TEST_EXISTED", "Question with test existed", 400),
    LESSON_NOT_FOUND("LESSON_NOT_FOUND", "Lesson not found!", 404),
    TEST_NOT_FOUND("TEST_NOT_FOUND", "Test not found!", 404),
    MOCK_TEST_AND_FULL_TEST_AND_DEMO_TEST_MUST_BE_IMPORT("MOCK_TEST_AND_FULL_TEST_AND_DEMO_TEST_MUST_BE_IMPORT",
            "Mock test, Full test and Demo test must be import!", 400),
    CLASS_LESSON_NOT_FOUND("CLASS_LESSON_NOT_FOUND", "Class lesson not found!", 404),
    USER_TEST_NOT_FOUND("USER_TEST_NOT_FOUND", "User Test not found!", 404),
    TEST_CONFIG_INVALID("TEST_CONFIG_INVALID", "Test config invalid", 400),
    QUESTION_OF_CONTENT_GROUP_NOT_ENOUGH("QUESTION_OF_CONTENT_GROUP_NOT_ENOUGH", "Question not enough",
            400),
    QUESTION_OF_LESSON_NOT_ENOUGH("QUESTION_OF_LESSON_NOT_ENOUGH", "Question of lesson not enough", 400),
    QUESTION_OF_CLASS_LESSON_NOT_ENOUGH("QUESTION_OF_CLASS_LESSON_NOT_ENOUGH",
            "Question of class lesson not is enough", 400),
    QUIZ_LESSON_NOT_FOUND("QUIZ_LESSON_NOT_FOUND", "Quiz lesson not found", 404),
    LESSON_NOT_MATCH_ANY_TYPE("LESSON_NOT_MATCH_ANY_TYPE", "Lesson not match any type", 400),
    FLASH_CARD_NOT_FOUND("FLASH_CARD_NOT_FOUND", "Flash card not found", 404),
    CLASS_USER_NOT_FOUND("CLASS_USER_NOT_FOUND", "Class User not found", 404),
    RESULT_ANALYSES_NOT_FOUND("RESULT_ANALYSES_NOT_FOUND", "Result analyses not found!", 404),
    THIS_ACCOUNT_NO_HAVE_DATA_FOR_THIS_TEST("THIS_ACCOUNT_NO_HAVE_DATA_FOR_THIS_TEST",
            "This account no have data for this test", 404),
    TEST_ALREADY_IS_QUIZ_LESSON("TEST_ALREADY_IS_QUIZ_LESSON", "Test already is quiz lesson", 400),
    TEST_DEMO_IS_EMPTY("TEST_DEMO_IS_EMPTY", "Test demo is empty", 404),
    QUIZ_LESSON_NOT_ASSIGNED_ANY_TEST("QUIZ_LESSON_NOT_ASSIGNED_ANY_TEST",
            "Quiz lesson not assigned any test", 400),
    BODY_QUESTION_MUST_NOT_BE_EMPTY_OR_BLANK("BODY_QUESTION_MUST_NOT_BE_EMPTY_OR_BLANK",
            "Body question must not be empty or blank", 400),
    EMAIL_IS_WRONG_FORMAT("EMAIL_IS_WRONG_FORMAT", "Email is wrong format", 404),
    EMAIL_IS_DUPLICATE("EMAIL_IS_DUPLICATE", "Email is duplicate", 404),
    KEYWORD_MUST_BE_UNIQUE("KEYWORD_MUST_BE_UNIQUE", "Keyword must be unique!", 400),

    PHONE_NUMBER_IS_WRONG_FORMAT("PHONE_NUMBER_IS_WRONG_FORMAT", "Phone Number is wrong format", 404),
    FULL_NAME_IS_BLANK("FULL_NAME_IS_BLANK", "FullName is wrong format", 404);

    private final static List<ErrorType> VALUES = Collections.unmodifiableList(Arrays.asList(ErrorTypes.values()));
    final String errorCode;
    String errorDetail;
    int statusCode;

    public static ErrorType getByErrorCode(String errorCode) {
        return VALUES.stream().filter(e -> e.getErrorCode().equalsIgnoreCase(errorCode)).findFirst().orElse(CommonErrorTypes.INTERNAL_SERVER_ERROR);
    }

    public static void initialize() {
        ErrorTypeFactory.addTypes(VALUES);
    }
}
