package swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import swp490.g7.OnlineLearningSystem.entities.question.domain.response.AnswerOptionResponseDto;

import java.util.List;

@Data
public class TestQuestionRandomDto {
    @JsonProperty("questionId")
    private Long questionId;

    @JsonProperty("body")
    private String body;

    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("lessonId")
    private Long lessonId;

    @JsonProperty("lessonName")
    private String lessonName;

    @JsonProperty("explanation")
    private String explanation;

    @JsonProperty("source")
    private String source;

    @JsonProperty("page")
    private String page;

    @JsonProperty("isCorrect")
    private Boolean isCorrect;

    @JsonProperty("groupId")
    private Long groupId;

    @JsonProperty("domain")
    private String domain;

    @JsonProperty("marked")
    private Boolean marked;

    @JsonProperty("answerOptions")
    private List<AnswerOptionResponseDto> answerOptions;
}
