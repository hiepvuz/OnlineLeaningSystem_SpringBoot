package swp490.g7.OnlineLearningSystem.entities.flashcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import swp490.g7.OnlineLearningSystem.entities.flashcard.domain.entity.FlashcardConfig;

import java.util.List;

public interface FlashcardConfigRepository extends JpaRepository<FlashcardConfig, Long> {
    @Query(value = "select f from FlashcardConfig f where f.flashcard.id = :flashcardId")
    List<FlashcardConfig> findFlascardConfigByFlashcardId(@Param("flashcardId") Long flashcardId);
}
