package swp490.g7.OnlineLearningSystem.entities.permission.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class PermissionResponseDto implements Serializable {

    @JsonProperty("screenId")
    Long screenId;

    @JsonProperty("roleId")
    Long roleId;

    @JsonProperty("screenName")
    String screenName;

    @JsonProperty("roleName")
    String roleName;

    @JsonProperty("allData")
    Boolean allData;

    @JsonProperty("canDelete")
    Boolean canDelete;

    @JsonProperty("canAdd")
    Boolean canAdd;

    @JsonProperty("canEdit")
    Boolean canEdit;

    public PermissionResponseDto(Long roleId, Long screenId, Boolean allData,
                                 Boolean canAdd, Boolean canDelete, Boolean canEdit, String screenName, String roleName) {
        this.screenId = screenId;
        this.roleId = roleId;
        this.screenName = screenName;
        this.roleName = roleName;
        this.allData = allData;
        this.canDelete = canDelete;
        this.canAdd = canAdd;
        this.canEdit = canEdit;
    }
}
