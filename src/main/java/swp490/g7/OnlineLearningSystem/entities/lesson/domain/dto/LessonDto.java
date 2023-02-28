package swp490.g7.OnlineLearningSystem.entities.lesson.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class LessonDto {
    @JsonProperty("lessonId")
    private Long lessonId;

    @JsonProperty("moduleId")
    private Long moduleId;

    @JsonProperty("subjectId")
    private Long subjectId;

    @JsonProperty("moduleName")
    private String moduleName;

    @JsonProperty("subjectName")
    private String subjectName;

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

    @JsonProperty("updatedOrder")
    private Date updatedOrder;

    @JsonProperty("attachmentUrl")
    private String attachmentUrl;

    @JsonProperty("isCompleted")
    private Boolean isCompleted;

    @JsonProperty("duration")
    private Integer duration;
}
