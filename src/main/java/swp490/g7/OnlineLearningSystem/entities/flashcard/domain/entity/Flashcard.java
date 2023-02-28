package swp490.g7.OnlineLearningSystem.entities.flashcard.domain.entity;

import lombok.Getter;
import lombok.Setter;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.SubjectSetting;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "flashcard")
@Getter
@Setter
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "review_purpose", columnDefinition = "TEXT")
    private String reviewPurpose;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "subject_setting_id", referencedColumnName = "subject_setting_id")
    private SubjectSetting subjectSetting;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "flashcard", orphanRemoval = true)
    private List<FlashcardConfig> flashcardConfigs = new LinkedList<>();

    @Column(name = "is_delete")
    private Boolean isDelete = false;
}
