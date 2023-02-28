package swp490.g7.OnlineLearningSystem.entities.lesson.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LessonConfigTestDto {
    @JsonProperty("lessonId")
    Long lessonId;

    @JsonProperty("name")
    String name;

    @JsonProperty("typeLesson")
    String typeLesson;

    @JsonProperty("numberOfQuestion")
    Long numberOfQuestion;
}
