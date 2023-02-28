package swp490.g7.OnlineLearningSystem.entities.lesson.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LessonRequestDto {
    @JsonProperty("moduleId")
    Long moduleId;

    @JsonProperty("subjectId")
    Long subjectId;

    @JsonProperty("status")
    Boolean status;

    @JsonProperty("name")
    String name;

    @JsonProperty("displayOrder")
    Integer displayOrder;

    @JsonProperty("typeLesson")
    String typeLesson;

    @JsonProperty("videoUrl")
    String videoUrl;

    @JsonProperty("lessonText")
    String lessonText;

    @JsonProperty("attachmentUrl")
    String attachmentUrl;

    @JsonProperty("testId")
    Long testId;

    @JsonProperty("duration")
    Integer duration;
}
