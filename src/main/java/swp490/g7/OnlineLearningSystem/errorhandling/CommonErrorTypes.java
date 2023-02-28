package swp490.g7.OnlineLearningSystem.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public enum CommonErrorTypes implements ErrorType {
    ACCOUNT_NOT_FOUND("ACCOUNT_NOT_FOUND", "Account not found", 404),
    VALIDATION_ERROR("VALIDATION_ERROR", "Validation error", 400),
    NOT_FOUND("NOT_FOUND", "Not found.", 404),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED("HTTP_REQUEST_METHOD_NOT_SUPPORTED",
            "Http request method not supported", 405),
    INVALID_INPUT("INVALID_INPUT", "Invalid input", 400),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Internal server error", 500),
    METHOD_ARGUMENT_TYPE_MISMATCH("METHOD_ARGUMENT_TYPE_MISMATCH", "Method argument type mismatch",
            400),
    REFRESH_TOKEN_FAILED("REFRESH_TOKEN_FAILED", "Refesh token failed!", 500);

    private final static List<ErrorType> VALUES = Collections.unmodifiableList(Arrays.asList(CommonErrorTypes.values()));

    String errorCode;
    String errorDetail;
    int statusCode;

    public static ErrorType getByErrorCode(String errorCode) {
        return VALUES.stream().filter(e -> e.getErrorCode().equalsIgnoreCase(errorCode)).findFirst().orElse(INTERNAL_SERVER_ERROR);
    }

    public static void initialize() {
        ErrorTypeFactory.addTypes(VALUES);
    }
}
