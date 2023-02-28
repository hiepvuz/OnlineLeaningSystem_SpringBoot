package swp490.g7.OnlineLearningSystem.entities.content_group.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swp490.g7.OnlineLearningSystem.entities.flashcard.domain.entity.FlashcardConfig;
import swp490.g7.OnlineLearningSystem.entities.question.domain.Question;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "content_group")
public class ContentGroup {
    public static final Long SUBJECT_CONTENT_GROUP_TYPE = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id")
    Long groupId;

    @Column(name = "name")
    String name;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "status")
    Boolean status;

    @Column(name = "type_id")
    Long typeId;

    @OneToMany(mappedBy = "contentGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<KeywordGroup> keywordGroups;

    @ManyToMany(mappedBy = "contentGroups", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<Question> questions;

    @OneToMany(mappedBy = "contentGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<FlashcardConfig> flashcardConfigs = new LinkedList<>();
}
