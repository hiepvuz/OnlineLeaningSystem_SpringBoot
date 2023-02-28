package swp490.g7.OnlineLearningSystem.entities.question.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.ContentGroup;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;
import swp490.g7.OnlineLearningSystem.entities.user_test.domain.UserAnswer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @Column(name = "test_id")
    private Long testId;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "class_lesson_id")
    private Long classLessonId;

    @Column(name = "explanation", columnDefinition = "TEXT")
    private String explanation;

    @Column(name = "source")
    private String source;

    @Column(name = "page")
    private String page;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<AnswerOption> answerOptions;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "question_group",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    Set<ContentGroup> contentGroups;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    List<UserAnswer> userAnswers;
}
