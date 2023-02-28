package swp490.g7.OnlineLearningSystem.entities.packages.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class GradesClassDto {

    @JsonProperty("classLessonId")
    Long classLessonId;

    @JsonProperty("classLessonName")
    String classLessonName;

    @JsonProperty("typeLesson")
    String typeLesson;

    @JsonProperty("duration")
    Integer duration;

    @JsonProperty("passRate")
    Integer passRate;

    @JsonProperty("grade")
    Double grade;

    @JsonProperty("totalOfQuestion")
    Integer totalOfQuestion;

    @JsonProperty("startedDate")
    Date startedDate;

    @JsonProperty("isPassed")
    Boolean isPassed;
}
