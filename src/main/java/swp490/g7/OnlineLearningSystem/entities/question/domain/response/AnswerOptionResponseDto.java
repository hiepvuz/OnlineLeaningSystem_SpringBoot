package swp490.g7.OnlineLearningSystem.entities.question.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AnswerOptionResponseDto {

    @JsonProperty("id")
    Long id;

    @JsonProperty("answerText")
    String answerText;

    @JsonProperty("isKey")
    Boolean isKey;

    @JsonProperty("userAnswer")
    Boolean userAnswer;
}
