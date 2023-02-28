package swp490.g7.OnlineLearningSystem.entities.test.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import swp490.g7.OnlineLearningSystem.entities.test.domain.TestConfig;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.other.TestResultDto;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestResponseDto {

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

    @JsonProperty("subjectId")
    Long subjectId;

    @JsonProperty("testConfigs")
    List<TestConfig> testConfigs;

    @JsonProperty("numberOfQuestion")
    Integer numberOfQuestion;

    @JsonProperty("result")
    TestResultDto testResult;
}
