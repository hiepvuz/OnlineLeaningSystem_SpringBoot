package swp490.g7.OnlineLearningSystem.entities.test.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TestFilterDto {
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

    @JsonProperty("subjectCode")
    String subjectCode;

    @JsonProperty("sourceId")
    Long sourceId;

    public TestFilterDto(Long testId, Integer testType, String name, Boolean status, Integer duration, Integer passRate,
                         Long subjectId, String subjectCode, Long sourceId) {
        this.testId = testId;
        this.testType = testType;
        this.name = name;
        this.status = status;
        this.duration = duration;
        this.passRate = passRate;
        this.subjectId = subjectId;
        this.subjectCode = subjectCode;
        this.sourceId = sourceId;
    }
}
