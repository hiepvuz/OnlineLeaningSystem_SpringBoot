package swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.other;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TestResultDto {
    @JsonProperty("userTestId")
    Long userTestId;

    @JsonProperty("startedDate")
    Date startedDate;

    @JsonProperty("isPassed")
    Boolean isPassed;

    @JsonProperty("result")
    Double result;

    @JsonProperty("corrects")
    Integer corrects;

    @JsonProperty("total")
    Integer total;
}
