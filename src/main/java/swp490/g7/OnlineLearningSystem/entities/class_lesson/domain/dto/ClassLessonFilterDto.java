package swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ClassLessonFilterDto {

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

    @JsonProperty("classSettingTitle")
    private String classSettingTitle;

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

    @JsonProperty("attachmentUrl")
    private String attachmentUrl;

    @JsonProperty("displayOrder")
    private Integer displayOrder;

    @JsonProperty("updatedOrder")
    private Date updatedOrder;

    @JsonProperty("duration")
    private Integer duration;

    @JsonProperty("testId")
    private Long testId;

    public ClassLessonFilterDto(Long lessonId, Long subjectId, String subjectName, Long classId, String classCode,
                                Boolean status, Long moduleId, String name, String typeLesson, String videoUrl,
                                String lessonText, String attachmentUrl, Integer displayOrder, Date updatedOrder,
                                String classSettingTitle, Integer duration, Long testId) {
        this.classLessonId = lessonId;
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
        this.classId = classId;
        this.classCode = classCode;
        this.updatedOrder = updatedOrder;
        this.classSettingTitle = classSettingTitle;
        this.duration = duration;
        this.testId = testId;
    }
}
