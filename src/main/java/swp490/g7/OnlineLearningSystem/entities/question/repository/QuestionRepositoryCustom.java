package swp490.g7.OnlineLearningSystem.entities.question.repository;

import swp490.g7.OnlineLearningSystem.entities.question.domain.Question;
import swp490.g7.OnlineLearningSystem.entities.question.domain.response.QuestionResponseDto;

import java.util.List;

public interface QuestionRepositoryCustom {
    QuestionResponseDto getQuestionById(Long id);

    List<QuestionResponseDto> filter(Long subjectId, Long testId, Long lessonId, String body);

    List<Question> findByContentGroupNotIsTestAndQuizLesson(Long contentGroupId);
}
