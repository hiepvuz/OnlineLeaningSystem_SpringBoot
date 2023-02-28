package swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContentGroupConfigTestDtoV2 {
    @JsonProperty("groupId")
    Long groupId;

    @JsonProperty("name")
    String name;

    @JsonProperty("typeId")
    Long typeId;

    @JsonProperty("numberOfQuestion")
    Integer numberOfQuestion;

    @JsonProperty("totalKeywordQuantity")
    Long totalKeywordQuantity;
}
