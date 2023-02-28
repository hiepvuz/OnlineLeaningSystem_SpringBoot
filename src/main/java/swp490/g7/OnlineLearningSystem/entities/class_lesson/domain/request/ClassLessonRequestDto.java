package swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClassLessonRequestDto {

    @JsonProperty("classId")
    Long classId;

    @JsonProperty("moduleId")
    Long moduleId;

    @JsonProperty("subjectId")
    Long subjectId;

    @JsonProperty("lessonId")
    Long lessonId;

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
