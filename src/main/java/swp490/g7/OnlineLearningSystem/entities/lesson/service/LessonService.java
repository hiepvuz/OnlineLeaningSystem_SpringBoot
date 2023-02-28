package swp490.g7.OnlineLearningSystem.entities.lesson.service;

import org.springframework.data.domain.Pageable;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.dto.LessonConfigTestDto;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.dto.LessonModuleDto;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.request.LessonRequestDto;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.response.LessonResponseDto;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.response.NormalLessonResponse;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.response.QuizLessonResponse;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.List;

public interface LessonService {
    LessonResponseDto findById(Long lessonId);

    LessonResponseDto create(LessonRequestDto request);

    LessonResponseDto update(Long lessonId, LessonRequestDto request);

    PaginationResponse findAll(Long testId, Long subjectId, Long moduleId, Boolean status,
                               String typeLesson, String name, Pageable pageable);

    PaginationResponse getAllBySubjectModule(Long subjectId, Long moduleId, Pageable pageable);

    void delete(Long lessonId);

    List<LessonModuleDto> filter(Long subjectId, Long testId, Long moduleId, Boolean status, String typeLesson,
                                 String name, Pageable pageable);

    List<LessonConfigTestDto> getAllForTestConfig(Long subjectId, Long moduleId);

    void enable(Long id);

    void disable(Long id);

    void submit(Long id);

    List<NormalLessonResponse> getAllNormalLessonBySubjectId(Long subjectId);

    List<QuizLessonResponse> getAllQuizLessonBySubjectId(Long subjectId);
}
