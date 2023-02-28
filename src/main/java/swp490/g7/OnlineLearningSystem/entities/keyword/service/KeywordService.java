package swp490.g7.OnlineLearningSystem.entities.keyword.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import swp490.g7.OnlineLearningSystem.entities.keyword.domain.request.KeywordRequestDto;
import swp490.g7.OnlineLearningSystem.entities.keyword.domain.response.KeywordResponseDto;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

public interface KeywordService {
    PaginationResponse findByGroupId(Long contentGroupId, Long subjectId, Pageable pageable);

    KeywordResponseDto findById(Long id);

    KeywordResponseDto save(KeywordRequestDto request);

    KeywordResponseDto update(Long id, KeywordRequestDto request);

    PaginationResponse filter(String keyword, Boolean status, Long categoryId, Long groupId, Long subjectId, Pageable pageable);

    void enable(Long id);

    void disable(Long id);

    void importKeyWord(Long categoryId, MultipartFile file);
}
