package swp490.g7.OnlineLearningSystem.entities.flashcard.domain.entity;

import lombok.Getter;
import lombok.Setter;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.ContentGroup;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "flashcard_config")
@Entity
@Getter
@Setter
public class FlashcardConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", referencedColumnName = "group_id")
    private ContentGroup contentGroup;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "flashcard_id", referencedColumnName = "id")
    private Flashcard flashcard;

    //Manual set data type in db of this column -> TEXT or LONG TEXT
    @Column(name = "keyword_list")
    private String keywordList;
}
