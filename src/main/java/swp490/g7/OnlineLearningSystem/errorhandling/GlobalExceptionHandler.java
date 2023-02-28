package swp490.g7.OnlineLearningSystem.errorhandling;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logbackLogger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle all EntityNotFoundExceptions and return the error object contained in the exception.
     *
     * @param exception EntityNotFoundException propagated to this class.
     * @return ResponseEntity which will contain an Error object.
     */
    @ExceptionHandler(OnlineLearningException.class)
    public ResponseEntity<ErrorResponse> handleOLSException(OnlineLearningException exception) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(exception.getError().getErrorCode());
        error.setErrorParams(exception.getErrorParams());
        error.setValidationErrors(exception.getValidationErrors());
        return ResponseEntity.status(exception.getStatus()).body(error);
    }

    /**
     * Handle all classes of exceptions and return the message contained in the exception.
     *
     * @param exception Exception propagated to this class.
     * @return ResponseEntity which will contain an Error object.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        OnlineLearningException error = new OnlineLearningException(exception, CommonErrorTypes.INTERNAL_SERVER_ERROR);
        return handleOLSException(error);
    }

    /**
     * Handle all HttpRequestMethodNotSupportedExceptions and return the message contained in the
     * exception.
     *
     * @param exception HttpRequestMethodNotSupportedException propagated to this class.
     * @param headers   the headers
     * @param status    the status
     * @param request   the request
     * @return ResponseEntity which will contain an Error object.
     */
    @Override
    protected ResponseEntity handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException exception, HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        OnlineLearningException error = new OnlineLearningException(CommonErrorTypes.HTTP_REQUEST_METHOD_NOT_SUPPORTED);
        return handleOLSException(error);
    }

    /**
     * Handle the invalid argument in controller method
     *
     * @param exception exception
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception) {
        OnlineLearningException error = new OnlineLearningException(CommonErrorTypes.METHOD_ARGUMENT_TYPE_MISMATCH, exception.getValue().toString());
        return handleOLSException(error);
    }

    /**
     * Handles the model validation and returns the constraint violation message if any.
     *
     * @return ResponseEntity which will contain an Error object.
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ex.getConstraintViolations())) {
            for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
                errors.add(violation.getMessage());
            }
        }
        OnlineLearningException tunnelException = new OnlineLearningException(CommonErrorTypes.VALIDATION_ERROR, errors.toArray(new String[0]));
        return handleOLSException(tunnelException);
    }

    /**
     * Handles the deserialization error
     *
     * @return ResponseEntity which will contain an Error object.
     */
    @Override
    protected ResponseEntity handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        OnlineLearningException error = new OnlineLearningException(CommonErrorTypes.INVALID_INPUT);
        return handleOLSException(error);
    }

    /**
     * Handles the validation field of object. Need of @Valid before Object parameter at Controller
     *
     * @param ex      exception
     * @param headers headers
     * @param status  status
     * @param request request
     * @return
     */
    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ValidationError> validationErrors = new ArrayList<>();
        for (ObjectError error : ex.getAllErrors()) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                ValidationError validationError = new ValidationError();
                validationError.setFieldName(fieldError.getField());
                validationError.setErrorCode(ErrorType.ErrorTypeFactory.getErrorType(fieldError.getDefaultMessage()));
                validationErrors.add(validationError);
            }
        }
        OnlineLearningException error = new OnlineLearningException(validationErrors);
        return handleOLSException(error);
    }
}