package swp490.g7.OnlineLearningSystem.entities.flashcard.service;

import swp490.g7.OnlineLearningSystem.entities.flashcard.domain.dto.FlashcardDto;
import swp490.g7.OnlineLearningSystem.entities.keyword.domain.Keyword;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.List;

public interface FlashcardService {
    FlashcardDto createOrUpdateFlashCard(FlashcardDto flashcardDto);

    FlashcardDto getFlashcardById(Long id);

    PaginationResponse<Object> getListFlashCardByContentGroupId(Long contentGroupId, Integer page, Integer size);

    void deleteFlashcardById(Long id);

    List<List<Keyword>> findKeywordEntitiesByFlashcardId(Long flashcardId);
}
