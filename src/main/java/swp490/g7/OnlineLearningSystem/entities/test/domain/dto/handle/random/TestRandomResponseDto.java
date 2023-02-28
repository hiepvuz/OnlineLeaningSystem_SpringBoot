package swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestRandomResponseDto {
    @JsonProperty("testId")
    Long testId;

    @JsonProperty("testType")
    Integer testType;

    @JsonProperty("name")
    String name;

    @JsonProperty("duration")
    Integer duration;

    @JsonProperty("passRate")
    Integer passRate;

    @JsonProperty("configType")
    String configType;

    @JsonProperty("totalTimeSeconds")
    Integer totalTimeSeconds;

    @JsonProperty("testConfigs")
    List<TestConfigDto> testConfigs;

    @JsonProperty("questions")
    List<TestQuestionRandomDto> questions;
}
