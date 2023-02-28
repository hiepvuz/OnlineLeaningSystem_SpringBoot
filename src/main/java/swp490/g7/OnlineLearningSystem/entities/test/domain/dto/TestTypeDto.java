package swp490.g7.OnlineLearningSystem.entities.test.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TestTypeDto {

    @JsonProperty("testType")
    Integer testType;

    @JsonProperty("tests")
    List<TestDto> tests;
}
