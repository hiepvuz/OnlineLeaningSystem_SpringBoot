package swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TestAnswerDto {
    @JsonProperty("answerText")
    String answerText;
}
