package swp490.g7.OnlineLearningSystem.entities.permission.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PermissionRequestDto {

    @JsonProperty("screenId")
    Long screenId;

    @JsonProperty("roleId")
    Long roleId;

    @JsonProperty("allData")
    Boolean allData;

    @JsonProperty("canDelete")
    Boolean canDelete;

    @JsonProperty("canAdd")
    Boolean canAdd;

    @JsonProperty("canEdit")
    Boolean canEdit;
}
