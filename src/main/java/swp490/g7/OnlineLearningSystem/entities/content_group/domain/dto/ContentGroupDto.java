package swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContentGroupDto {
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

    @JsonProperty("totalKeyword")
    Integer totalKeyword;
}
