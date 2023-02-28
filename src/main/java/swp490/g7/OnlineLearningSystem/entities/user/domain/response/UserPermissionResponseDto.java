package swp490.g7.OnlineLearningSystem.entities.user.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import swp490.g7.OnlineLearningSystem.entities.permission.domain.response.PermissionResponseDto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
public class UserPermissionResponseDto implements Serializable {
    @JsonProperty("userId")
    Long userId;

    @JsonProperty("username")
    String username;

    @JsonProperty("fullName")
    String fullName;

    @JsonProperty("email")
    String email;

    @JsonProperty("avatarUrl")
    String avatarUrl;

    @JsonProperty("phoneNumber")
    String phoneNumber;

    @JsonProperty("disable")
    private boolean disable;

    @JsonProperty("roles")
    Set<Object> roles;

    @JsonProperty("permissions")
    List<PermissionResponseDto> permissions;
}
