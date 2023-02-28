package swp490.g7.OnlineLearningSystem.entities.question.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ContentGroupExportDto;
import swp490.g7.OnlineLearningSystem.entities.question.domain.response.AnswerOptionResponseDto;

import java.util.List;

@Data
public class QuestionExportDto {
    @JsonProperty("questionId")
    Long questionId;

    @JsonProperty("body")
    String body;

    @JsonProperty("testId")
    Long testId;

    @JsonProperty("testType")
    String testType;

    @JsonProperty("subjectId")
    Long subjectId;

    @JsonProperty("subjectName")
    String subjectName;

    @JsonProperty("imageUrl")
    String imageUrl;

    @JsonProperty("lessonId")
    Long lessonId;

    @JsonProperty("explanation")
    String explanation;

    @JsonProperty("source")
    String source;

    @JsonProperty("page")
    String page;

    @JsonProperty("answerOptions")
    List<AnswerOptionResponseDto> answerOptions;

    @JsonProperty("contentGroups")
    List<ContentGroupExportDto> contentGroups;
}
