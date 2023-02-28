package swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContentGroupConfigTestDto {
    @JsonProperty("groupId")
    Long groupId;

    @JsonProperty("name")
    String name;

    @JsonProperty("typeId")
    Long typeId;

    @JsonProperty("numberOfQuestion")
    Integer numberOfQuestion;
}
