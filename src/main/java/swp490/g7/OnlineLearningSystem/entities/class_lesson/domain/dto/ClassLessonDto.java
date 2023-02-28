package swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ClassLessonDto {
    @JsonProperty("classLessonId")
    private Long classLessonId;

    @JsonProperty("moduleId")
    private Long moduleId;

    @JsonProperty("subjectId")
    private Long subjectId;

    @JsonProperty("subjectName")
    private String subjectName;

    @JsonProperty("classId")
    private Long classId;

    @JsonProperty("classCode")
    private String classCode;

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("name")
    private String name;

    @JsonProperty("typeLesson")
    private String typeLesson;

    @JsonProperty("videoUrl")
    private String videoUrl;

    @JsonProperty("lessonText")
    private String lessonText;

    @JsonProperty("displayOrder")
    private Integer displayOrder;

    @JsonIgnore
    private Date updatedOrder;

    @JsonProperty("moduleName")
    private String classSettingTitle;

    @JsonProperty("attachmentUrl")
    String attachmentUrl;

    @JsonProperty("isCompleted")
    private Boolean isCompleted;

    @JsonProperty("duration")
    private Integer duration;

    @JsonProperty("testId")
    private Long testId;
}
