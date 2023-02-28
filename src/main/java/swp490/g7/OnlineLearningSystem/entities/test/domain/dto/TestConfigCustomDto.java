package swp490.g7.OnlineLearningSystem.entities.test.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TestConfigCustomDto {

    @JsonProperty("id")
    Long id;

    @JsonProperty("testId")
    Long testId;

    @JsonProperty("groupId")
    Long groupId;

    @JsonProperty("contentGroupTitle")
    String contentGroupTitle;

    @JsonProperty("lessonId")
    Long lessonId;

    @JsonProperty("lessonName")
    String lessonName;

    @JsonProperty("quantity")
    Integer quantity;
}
