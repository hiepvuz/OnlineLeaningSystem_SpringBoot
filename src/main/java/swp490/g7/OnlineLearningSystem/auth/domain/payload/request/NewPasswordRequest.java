package swp490.g7.OnlineLearningSystem.auth.domain.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NewPasswordRequest {
    @JsonProperty("email")
    String email;

    @JsonProperty("verifyCode")
    String verifyCode;

    @JsonProperty("newPassword")
    String newPassword;
}
