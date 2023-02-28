package swp490.g7.OnlineLearningSystem.entities.flashcard.domain.dto;

import lombok.Getter;
import lombok.Setter;
import swp490.g7.OnlineLearningSystem.entities.flashcard.domain.req.KeywordEntities;

import java.util.List;

@Getter
@Setter
public class FlashcardDto {

    private Long id;

    private String title;

    private String reviewPurpose;

    private Long contentId;

    private List<KeywordEntities> keywordEntities;
}
