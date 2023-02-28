package swp490.g7.OnlineLearningSystem.entities.lesson.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class LessonFilterDto {

    @JsonProperty("lessonId")
    private Long lessonId;

    @JsonProperty("moduleId")
    private Long moduleId;

    @JsonProperty("moduleName")
    private String moduleName;

    @JsonProperty("subjectId")
    private Long subjectId;

    @JsonProperty("subjectName")
    private String subjectName;

    @JsonProperty("testId")
    private Long testId;

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

    @JsonProperty("attachmentUrl")
    private String attachmentUrl;

    @JsonProperty("displayOrder")
    private Integer displayOrder;

    @JsonIgnore
    private Date updatedOrder;

    @JsonProperty("duration")
    private Integer duration;

    public LessonFilterDto(Long lessonId, Long subjectId, String subjectName, Boolean status, Long moduleId,
                           String name, String typeLesson, String videoUrl, String lessonText, String attachmentUrl,
                           Integer displayOrder, Date updatedOrder, String moduleName, Integer duration) {
        this.lessonId = lessonId;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.status = status;
        this.moduleId = moduleId;
        this.name = name;
        this.typeLesson = typeLesson;
        this.videoUrl = videoUrl;
        this.lessonText = lessonText;
        this.attachmentUrl = attachmentUrl;
        this.displayOrder = displayOrder;
        this.updatedOrder = updatedOrder;
        this.moduleName = moduleName;
        this.duration = duration;
    }
}
