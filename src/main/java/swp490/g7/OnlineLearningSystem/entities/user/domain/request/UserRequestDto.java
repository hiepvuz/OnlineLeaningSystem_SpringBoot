package swp490.g7.OnlineLearningSystem.entities.user.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
public class UserRequestDto {

    @JsonProperty("username")
    private String username;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("address")
    private String address;

    @JsonProperty("email")
    private String email;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("avatarUrl")
    private String avatarUrl;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("disable")
    private boolean disable;

    @JsonProperty("roles")
    private Set<String> roles;

}
