package swp490.g7.OnlineLearningSystem.entities.question.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import swp490.g7.OnlineLearningSystem.entities.question.domain.dto.QuestionExportDto;
import swp490.g7.OnlineLearningSystem.entities.question.domain.request.QuestionRequestDto;
import swp490.g7.OnlineLearningSystem.entities.question.domain.response.QuestionResponseDto;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.io.IOException;
import java.util.List;

public interface QuestionService {
    PaginationResponse filter(Long subjectId, Long testId, Long lessonId, String body, Long groupId, Pageable pageable);

    QuestionResponseDto findById(Long questionId);

    QuestionResponseDto create(QuestionRequestDto request);

    QuestionResponseDto update(Long questionId, QuestionRequestDto request);

    QuestionResponseDto getQuestionById(Long questionId);

    void delete(Long questionId);

    String imageUpload(Long id, MultipartFile file);

    void importQuestion(Long subject, Long lessonId, Long classLessonId, Long testId, MultipartFile zip) throws IOException;

    List<QuestionExportDto> exportQuestion(Long subjectId, Long testId, Long lessonId, String body, Long groupId);
}
