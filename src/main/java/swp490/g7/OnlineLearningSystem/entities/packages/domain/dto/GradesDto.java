package swp490.g7.OnlineLearningSystem.entities.packages.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class GradesDto {
    @JsonProperty("lessonId")
    Long lessonId;

    @JsonProperty("isPassed")
    Boolean isPassed;

    @JsonProperty("lessonName")
    String lessonName;

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
}
