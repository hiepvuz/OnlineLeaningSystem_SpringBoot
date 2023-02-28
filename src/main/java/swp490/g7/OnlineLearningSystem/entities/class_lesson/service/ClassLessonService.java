package swp490.g7.OnlineLearningSystem.entities.class_lesson.service;

import org.springframework.data.domain.Pageable;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.dto.ClassLessonModuleDto;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.request.ClassLessonRequestDto;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.response.ClassLessonResponseDto;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.List;

public interface ClassLessonService {
    ClassLessonResponseDto findById(Long classLessonId);

    ClassLessonResponseDto create(ClassLessonRequestDto request);

    ClassLessonResponseDto update(Long classLessonId, ClassLessonRequestDto request);

    PaginationResponse findAll(Long subjectId, Long classId, Long moduleId, Boolean status,
                               String typeLesson, String name, Pageable pageable);

    PaginationResponse getAllByClassModule(Long subjectId, Pageable pageable);

    void delete(Long classLessonId);

    List<ClassLessonModuleDto> filter(Long subjectId, Long classId, Long moduleId, Boolean status, String typeLesson,
                                      String name);

    void enable(Long id);

    void disable(Long id);

    void submit(Long id);
}
