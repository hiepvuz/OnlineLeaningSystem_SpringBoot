package swp490.g7.OnlineLearningSystem.entities.content_group.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swp490.g7.OnlineLearningSystem.entities.keyword.domain.Keyword;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "keyword_group")
public class KeywordGroup {
    @EmbeddedId
    private KeywordGroupId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "keywordId")
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "groupId")
    @JoinColumn(name = "group_id")
    private ContentGroup contentGroup;
}
