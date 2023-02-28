package swp490.g7.OnlineLearningSystem.entities.content_group.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContentGroupResponseDto {

    @JsonProperty("groupId")
    Long groupId;

    @JsonProperty("name")
    String name;

    @JsonProperty("description")
    String description;

    @JsonProperty("status")
    Boolean status;

    @JsonProperty("typeId")
    Long typeId;

    @JsonProperty("subjectSettingTitle")
    String subjectSettingTitle;
}
