package swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.dto.ClassDto;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.dto.SubjectLessonDto;

@Data
public class ClassLessonResponseDto {

    @JsonProperty("classLessonId")
    Long classLessonId;

    @JsonProperty("moduleId")
    Long moduleId;

    @JsonProperty("subject")
    SubjectLessonDto subject;

    @JsonProperty("class")
    ClassDto classDto;

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

    @JsonProperty("duration")
    Integer duration;

    @JsonProperty("testId")
    Long testId;

    @JsonProperty("testType")
    Integer testType;

    @JsonProperty("testTitle")
    String testTitle;

    @JsonProperty("numberOfQuestion")
    Integer numberOfQuestion;

    @JsonProperty("passRate")
    Integer passRate;

    @JsonProperty("isCompleted")
    Boolean isCompleted;
}
