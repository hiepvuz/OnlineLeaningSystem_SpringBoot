package swp490.g7.OnlineLearningSystem.entities.test.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TestConfigRequestDto {
    @JsonProperty("id")
    Long id;

    @JsonProperty("groupId")
    Long groupId;

    @JsonProperty("quantity")
    Integer quantity;

    @JsonProperty("lessonId")
    Long lessonId;

    @JsonProperty("classLessonId")
    Long classLessonId;
}
