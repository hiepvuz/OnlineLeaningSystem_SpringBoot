package swp490.g7.OnlineLearningSystem.entities.flashcard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swp490.g7.OnlineLearningSystem.entities.flashcard.domain.dto.FlashcardDto;
import swp490.g7.OnlineLearningSystem.entities.flashcard.service.FlashcardService;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

@RestController
@RequestMapping("/flashcards")
public class FlashcardController {
    @Autowired
    private FlashcardService flashcardService;

    @PostMapping("/create")
    public ResponseEntity<?> createFlashcard(@RequestBody FlashcardDto flashcard) {
        return ResponseEntity.ok(flashcardService.createOrUpdateFlashCard(flashcard));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getFlashcardById(@PathVariable Long id) {
        return ResponseEntity.ok(flashcardService.getFlashcardById(id));
    }

    @GetMapping("")
    public PaginationResponse<Object> getListFlashcardByContentGroupId(@RequestParam(name = "contentGroupId", defaultValue = "") Long contentGroupId,
                                                                       @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                                       @RequestParam(name = "pageIndex", defaultValue = "0") Integer page) {
        return flashcardService.getListFlashCardByContentGroupId(contentGroupId, page, size);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFlashcard(@PathVariable Long id) {
        flashcardService.deleteFlashcardById(id);
        return ResponseEntity.ok("Delete Sucessfully!");
    }

    @GetMapping("/review")
    public ResponseEntity<?> getListKeyWordByContentIdAndFlashcardId(@RequestParam(name = "flashcardId") Long flashcardId) {
        try {
            return ResponseEntity.ok(flashcardService.findKeywordEntitiesByFlashcardId(flashcardId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
