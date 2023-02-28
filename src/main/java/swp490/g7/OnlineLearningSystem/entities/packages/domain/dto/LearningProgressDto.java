package swp490.g7.OnlineLearningSystem.entities.packages.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LearningProgressDto {
    @JsonProperty("total")
    Long total;

    @JsonProperty("isCompleted")
    Long isCompleted;

    @JsonProperty("percent")
    Double percent;
}
