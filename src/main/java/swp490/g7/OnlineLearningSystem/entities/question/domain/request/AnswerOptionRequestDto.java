package swp490.g7.OnlineLearningSystem.entities.question.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AnswerOptionRequestDto {

    @JsonProperty("id")
    Long id;

    @JsonProperty("answerText")
    String answerText;

    @JsonProperty("isKey")
    Boolean isKey;
}
