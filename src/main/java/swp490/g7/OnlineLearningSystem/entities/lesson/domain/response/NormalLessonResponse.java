package swp490.g7.OnlineLearningSystem.entities.lesson.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NormalLessonResponse {
    @JsonProperty("lessonId")
    Long lessonId;

    @JsonProperty("name")
    String name;

    @JsonProperty("moduleId")
    Long moduleId;

    @JsonProperty("moduleName")
    String moduleName;
}
