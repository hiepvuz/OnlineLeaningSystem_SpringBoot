package swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SubjectClassLessonDto {
    @JsonProperty("subjectId")
    Long subjectId;

    @JsonProperty("subjectCode")
    String subjectCode;

    @JsonProperty("subjectName")
    String subjectName;

    @JsonProperty("categoryId")
    Long categoryId;

    @JsonProperty("status")
    Boolean status;

    @JsonProperty("body")
    String body;
}
