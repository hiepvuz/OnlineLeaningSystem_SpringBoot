package swp490.g7.OnlineLearningSystem.entities.user.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto implements Serializable {
    @JsonProperty("userId")
    Long userId;

    @JsonProperty("username")
    String username;

    @JsonProperty("fullName")
    String fullName;

    @JsonProperty("address")
    String address;

    @JsonProperty("email")
    String email;

    @JsonProperty("gender")
    String gender;

    @JsonProperty("avatarUrl")
    String avatarUrl;

    @JsonProperty("phoneNumber")
    String phoneNumber;

    @JsonProperty("createdDate")
    Date createdDate;

    @JsonProperty("updatedDate")
    Date updatedDate;

    @JsonProperty("disable")
    boolean disable;

    @JsonProperty("roles")
    Set<Object> roles;

    public UserResponseDto(Long userId, String username, String fullName, String address, String email, String gender,
                           String avatarUrl, String phoneNumber, Date createdDate, Date updatedDate, boolean disable, Set<Object> roles) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.address = address;
        this.email = email;
        this.gender = gender;
        this.avatarUrl = avatarUrl;
        this.phoneNumber = phoneNumber;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.disable = disable;
        this.roles = roles;
    }
}
