package swp490.g7.OnlineLearningSystem.entities.flashcard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swp490.g7.OnlineLearningSystem.entities.flashcard.domain.entity.Flashcard;

import java.util.Set;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    Page<Flashcard> findByIdIn(Set<Long> ids, Pageable pageable);

    @Query(value = "SELECT f FROM Flashcard f WHERE f.isDelete IS FALSE ")
    Page<Flashcard> getPageFlashcardByIsDelete(Pageable pageable);
}
