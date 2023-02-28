package swp490.g7.OnlineLearningSystem.entities.class_lesson.repository;

import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.dto.ClassLessonFilterDto;

import java.util.List;

public interface ClassLessonRepositoryCustom {
    List<ClassLessonFilterDto> findAll(Long subjectId, Long classId, Long moduleId, Boolean status,
                                       String typeLesson, String name);
}
