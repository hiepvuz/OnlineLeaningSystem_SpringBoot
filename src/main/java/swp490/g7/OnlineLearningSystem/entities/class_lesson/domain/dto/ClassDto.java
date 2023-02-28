package swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClassDto {
    @JsonProperty("classId")
    Long classId;

    @JsonProperty("classCode")
    String classCode;

    @JsonProperty("status")
    Boolean status;

    @JsonProperty("lessonText")
    String lessonText;
}
