package swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TestConfigDto {
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
