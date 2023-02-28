package swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.other;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TestHistoryDto {
    @JsonProperty("userTestId")
    Long userTestId;

    @JsonProperty("testId")
    Long testId;

    @JsonProperty("testType")
    Integer testType;

    @JsonProperty("name")
    String name;

    @JsonProperty("status")
    Boolean status;

    @JsonProperty("duration")
    Integer duration;

    @JsonProperty("passRate")
    Integer passRate;

    @JsonProperty("startedDate")
    Date startedDate;

    @JsonProperty("isPassed")
    Boolean isPassed;

    @JsonProperty("result")
    Double result;

    @JsonProperty("corrects")
    Integer corrects;

    @JsonProperty("numberOfQuestion")
    Integer numberOfQuestion;

    @JsonProperty("totalTime")
    Double totalTime;
}
