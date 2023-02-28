package swp490.g7.OnlineLearningSystem.entities.keyword.domain.dto;

import lombok.Getter;
import lombok.Setter;
import swp490.g7.OnlineLearningSystem.entities.keyword.domain.Keyword;

@Getter
@Setter
public class KeywordDto {
    Long keywordId;

    String keyword;

    String excerpt;

    String body;

    Boolean status;

    Long categoryId;

    public KeywordDto(Keyword keyword) {
        if (keyword != null) {
            this.keywordId = keyword.getKeywordId();
            this.keyword = keyword.getKeyword();
            this.excerpt = keyword.getExcerpt();
            this.body = keyword.getBody();
            this.status = keyword.getStatus();
            this.categoryId = keyword.getCategoryId();
        }
    }
}
