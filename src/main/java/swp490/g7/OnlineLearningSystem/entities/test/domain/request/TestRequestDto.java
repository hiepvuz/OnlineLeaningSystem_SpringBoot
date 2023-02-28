package swp490.g7.OnlineLearningSystem.entities.test.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TestRequestDto {

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

    @JsonProperty("description")
    String description;

    @JsonProperty("testConfigs")
    List<TestConfigRequestDto> testConfigs;
}
