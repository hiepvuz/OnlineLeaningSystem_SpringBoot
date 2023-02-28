package swp490.g7.OnlineLearningSystem.entities.flashcard.domain.res;

import lombok.Getter;
import lombok.Setter;
import swp490.g7.OnlineLearningSystem.entities.flashcard.domain.req.KeywordEntities;

import java.util.List;

@Getter
@Setter
public class FlashcardListResponse {
    private Long id;
    private String title;
    private Long contentId;
    private String reviewPurpose;
    private List<KeywordEntities> keywordEntities;
}
