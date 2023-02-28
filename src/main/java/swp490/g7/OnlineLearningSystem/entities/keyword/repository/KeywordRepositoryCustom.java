package swp490.g7.OnlineLearningSystem.entities.keyword.repository;

import swp490.g7.OnlineLearningSystem.entities.keyword.domain.response.KeywordResponseDto;

import java.util.List;

public interface KeywordRepositoryCustom {
    List<KeywordResponseDto> findByGroupId(Long contentGroupId, Long subjectId);

    KeywordResponseDto getKeywordById(Long id);

    List<KeywordResponseDto> filter(String keyword, Boolean status, Long categoryId, Long groupId, Long subjectId);
}
