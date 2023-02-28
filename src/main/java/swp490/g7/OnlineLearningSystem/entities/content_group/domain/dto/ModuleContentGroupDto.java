package swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ModuleContentGroupDto {
    @JsonProperty("subjectSettingId")
    Long moduleId;

    @JsonProperty("subjectSettingTitle")
    String moduleName;

    @JsonProperty("contentGroups")
    List<ContentGroupDto> contentGroups;
}
