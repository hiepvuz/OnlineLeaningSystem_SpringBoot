package swp490.g7.OnlineLearningSystem.entities.web_contact.domain.respone;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WebContactResponseDto {

    @JsonProperty("id")
    Long id;

    @JsonProperty("fullName")
    String fullName;

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

    @JsonProperty("username")
    String username;

    @JsonProperty("status")
    Boolean status;

}
