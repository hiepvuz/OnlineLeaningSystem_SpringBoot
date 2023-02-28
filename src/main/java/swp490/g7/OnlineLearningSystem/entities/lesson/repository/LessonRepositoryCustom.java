package swp490.g7.OnlineLearningSystem.entities.lesson.repository;

import swp490.g7.OnlineLearningSystem.entities.lesson.domain.dto.LessonFilterDto;

import java.util.List;

public interface LessonRepositoryCustom {
    List<LessonFilterDto> findAll(Long testId, Long subjectId, Long moduleId, Boolean status, String typeLesson, String name);
}
