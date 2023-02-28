package swp490.g7.OnlineLearningSystem.entities.keyword.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.KeywordGroup;
import swp490.g7.OnlineLearningSystem.entities.keyword.domain.dto.KeywordDto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "keyword")
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "keyword_id")
    Long keywordId;

    @Column(name = "keyword", unique = true)
    String keyword;

    @Column(name = "excerpt", columnDefinition = "TEXT")
    String excerpt;

    @Column(name = "body", columnDefinition = "TEXT")
    String body;

    @Column(name = "status")
    Boolean status;

    @Column(name = "categoryId")
    Long categoryId;

    @OneToMany(mappedBy = "keyword", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    List<KeywordGroup> keywordGroups;

    public Keyword(KeywordDto keywordDto) {
        if (keywordDto != null) {
            this.keywordId = keywordDto.getKeywordId();
            this.keyword = keywordDto.getKeyword();
            this.excerpt = keywordDto.getExcerpt();
            this.body = keywordDto.getBody();
            this.status = keywordDto.getStatus();
            this.categoryId = keywordDto.getCategoryId();
        }
    }
}
