package swp490.g7.OnlineLearningSystem.entities.web_contact.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class WebContactRequestDto {

    @JsonProperty("fullName")
    String fullName;

    @Email
    @JsonProperty("email")
    String email;

    @JsonProperty("phoneNumber")
    String phoneNumber;

    @JsonProperty("address")
    String address;

    @JsonProperty("message")
    String message;

    @JsonProperty("response")
    String response;

    @JsonProperty("userId")
    Long userId;

    @JsonProperty("settingId")
    Long settingId;

    @JsonProperty("status")
    Boolean status;

}
