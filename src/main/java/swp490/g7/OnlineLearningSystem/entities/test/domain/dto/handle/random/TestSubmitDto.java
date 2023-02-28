package swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random;

import swp490.g7.OnlineLearningSystem.entities.question.domain.response.AnswerOptionResponseDto;

import java.util.List;

public class TestSubmitDto {
    Long questionId;
    List<AnswerOptionResponseDto> answerOptions;
}
