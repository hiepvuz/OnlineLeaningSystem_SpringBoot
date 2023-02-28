package swp490.g7.OnlineLearningSystem.entities.question.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class QuestionRequestDto {

    @JsonProperty("body")
    String body;

    @JsonProperty("subjectId")
    Long subjectId;

    @JsonProperty("lessonId")
    Long lessonId;

    @JsonProperty("explanation")
    String explanation;

    @JsonProperty("source")
    String source;

    @JsonProperty("page")
    String page;

    @JsonProperty("testId")
    Long testId;

    @JsonProperty("answerOptions")
    List<AnswerOptionRequestDto> answerOptions;

    @JsonProperty("contentGroupIds")
    List<Long> contentGroupIds;
}
