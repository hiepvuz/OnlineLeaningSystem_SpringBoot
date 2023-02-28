package swp490.g7.OnlineLearningSystem.entities.lesson.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QuizLessonResponse {
    @JsonProperty("lessonId")
    Long lessonId;

    @JsonProperty("testId")
    Long testId;

    @JsonProperty("testName")
    String testName;

    @JsonProperty("moduleId")
    Long moduleId;

    @JsonProperty("moduleName")
    String moduleName;
}
