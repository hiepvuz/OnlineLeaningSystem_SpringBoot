package swp490.g7.OnlineLearningSystem.entities.test.repository;

import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.TestFilterDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.PrePareLessonConfigDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.QuestionContentQueryDto;

import java.util.List;

public interface TestRepositoryCustom {

    List<TestFilterDto> filter(Long subjectId, Integer testType, String name, Boolean status);

    List<TestFilterDto> getAllBySubject(Long id);

    List<Long> getListQuestionLesson(PrePareLessonConfigDto p);

    List<QuestionContentQueryDto> getListQuestionWithContentGroup(PrePareLessonConfigDto p);

    List<TestFilterDto> getAllTestForQuizLesson(Long subjectId);

}
