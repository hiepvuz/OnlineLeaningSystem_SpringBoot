package swp490.g7.OnlineLearningSystem.entities.content_group.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class KeywordGroupId implements Serializable {
    @Column(name = "keyword_id", nullable = false)
    private Long keywordId;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    public KeywordGroupId(Long keywordId, Long groupId) {
        this.keywordId = keywordId;
        this.groupId = groupId;
    }
}
