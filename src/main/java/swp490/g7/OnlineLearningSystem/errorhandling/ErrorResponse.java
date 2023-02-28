package swp490.g7.OnlineLearningSystem.errorhandling;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = -421066335031631692L;
    String errorCode;
    List<String> errorParams;
    List<ValidationError> validationErrors;
    String errorMessage;
}
