package swp490.g7.OnlineLearningSystem.entities.user_test.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swp490.g7.OnlineLearningSystem.entities.question.domain.Question;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_answer")
public class UserAnswer {
    @EmbeddedId
    private UserAnswerId userAnswerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "userTestId")
    @JoinColumn(name = "user_test_id")
    private UserTest userTest;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "questionId")
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "answers", columnDefinition = "TEXT")
    private String answers;

    @Column(name = "marked")
    private Boolean marked;

    @Column(name = "groupId")
    private Long groupId;
}
