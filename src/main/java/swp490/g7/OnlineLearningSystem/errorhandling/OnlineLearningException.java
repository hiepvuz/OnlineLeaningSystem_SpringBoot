package swp490.g7.OnlineLearningSystem.errorhandling;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode(callSuper = true)
@Data
public class OnlineLearningException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -5048054610805580510L;

    ErrorType error;
    List<String> errorParams;
    List<ValidationError> validationErrors;
    int status;

    public OnlineLearningException(Throwable cause) {
        this(cause, CommonErrorTypes.INTERNAL_SERVER_ERROR, CommonErrorTypes.INTERNAL_SERVER_ERROR.getStatusCode());
    }

    public OnlineLearningException(Throwable cause, ErrorType error, String... parameters) {
        this(cause, error, error.getStatusCode(), parameters);
    }

    public OnlineLearningException(Throwable cause, ErrorType error, int status, String... parameters) {
        this(cause, error, status, Collections.emptyList(), parameters);
    }

    public OnlineLearningException(ErrorType error, String... parameters) {
        this(error, error.getStatusCode(), parameters);
    }

    public OnlineLearningException(ErrorType error, int status, String... parameters) {
        this(null, error, status, Collections.emptyList(), parameters);
    }

    public OnlineLearningException(List<ValidationError> validationErrors) {
        this(validationErrors, CommonErrorTypes.VALIDATION_ERROR.getStatusCode());
    }

    public OnlineLearningException(List<ValidationError> validationErrors, int status) {
        this(null, CommonErrorTypes.VALIDATION_ERROR, status, validationErrors);
    }

    public OnlineLearningException(Throwable cause, ErrorType error, int status, List<ValidationError> validationErrors, String... parameters) {
        super(cause);
        this.error = error;
        this.errorParams = Stream.of(parameters).filter(ObjectUtils::isNotEmpty).collect(Collectors.toList());
        this.validationErrors = validationErrors;
        this.status = status;
    }

    public OnlineLearningException(String string) {
        super(string);
    }
}
