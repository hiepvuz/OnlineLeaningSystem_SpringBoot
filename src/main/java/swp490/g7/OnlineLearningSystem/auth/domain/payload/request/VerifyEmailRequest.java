package swp490.g7.OnlineLearningSystem.auth.domain.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import swp490.g7.OnlineLearningSystem.errorhandling.ValidationErrorCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class VerifyEmailRequest {
    @NotNull(message = ValidationErrorCode.EMAIL_CAN_NOT_BE_NULL_OR_EMPTY)
    @NotBlank(message = ValidationErrorCode.EMAIL_CAN_NOT_BE_NULL_OR_EMPTY)
    @JsonProperty("email")
    String email;

    @NotNull(message = ValidationErrorCode.USER_NAME_CAN_NOT_BE_NULL_OR_EMPTY)
    @NotBlank(message = ValidationErrorCode.USER_NAME_CAN_NOT_BE_NULL_OR_EMPTY)
    @JsonProperty("username")
    String userName;
}
