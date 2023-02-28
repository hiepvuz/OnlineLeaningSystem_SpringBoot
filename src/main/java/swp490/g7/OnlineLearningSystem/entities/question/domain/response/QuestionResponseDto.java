package swp490.g7.OnlineLearningSystem.entities.question.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ContentGroupDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QuestionResponseDto {
    @JsonProperty("questionId")
    private Long questionId;

    @JsonProperty("body")
    private String body;

    @JsonProperty("testId")
    private Long testId;

    @JsonIgnore
    @JsonProperty("testType")
    private String testType;

    @JsonProperty("subjectId")
    private Long subjectId;

    @JsonProperty("subjectName")
    private String subjectName;

    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("lessonId")
    private Long lessonId;

    @JsonProperty("explanation")
    private String explanation;

    @JsonProperty("source")
    private String source;

    @JsonProperty("page")
    private String page;

    @JsonProperty("lessonName")
    private String lessonName;

    @JsonProperty("answerOptions")
    private List<AnswerOptionResponseDto> answerOptions;

    @JsonProperty("isCorrect")
    Boolean isCorrect;

    @JsonProperty("contentGroups")
    List<ContentGroupDto> contentGroups;

    public QuestionResponseDto(Long questionId, String body, Long testId, String testType, Long subjectId,
                               String subjectName, String imageUrl, String explanation, String source, String page,
                               Long lessonId, String lessonName) {
        this.questionId = questionId;
        this.body = body;
        this.testId = testId;
        this.testType = testType;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.imageUrl = imageUrl;
        this.explanation = explanation;
        this.source = source;
        this.page = page;
        this.lessonId = lessonId;
        this.lessonName = lessonName;
    }

    public QuestionResponseDto(Long questionId, String body, Long testId, Long subjectId, String subjectName,
                               String imageUrl, String explanation, String source, String page, Long lessonId,
                               String lessonName) {
        this.questionId = questionId;
        this.body = body;
        this.testId = testId;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.imageUrl = imageUrl;
        this.explanation = explanation;
        this.source = source;
        this.page = page;
        this.lessonId = lessonId;
        this.lessonName = lessonName;
    }

    public QuestionResponseDto(Long questionId, String body, Long testId, String testType, Long subjectId,
                               String subjectName, String imageUrl, String explanation, String source, String page,
                               Long lessonId, List<AnswerOptionResponseDto> answerOptions, String lessonName) {
        this.questionId = questionId;
        this.body = body;
        this.testId = testId;
        this.testType = testType;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.imageUrl = imageUrl;
        this.explanation = explanation;
        this.source = source;
        this.page = page;
        this.lessonId = lessonId;
        this.answerOptions = answerOptions;
        this.lessonName = lessonName;
    }
}
