package swp490.g7.OnlineLearningSystem.entities.user_test.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swp490.g7.OnlineLearningSystem.entities.test.domain.Test;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_test")
public class UserTest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_test_id")
    private Long userTestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private Test test;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "class_lesson_id")
    private Long classLessonId;

    @Column(name = "total")
    private Integer total;

    @Column(name = "corrects")
    private Integer corrects;

    @Column(name = "startedDate")
    private Date startedDate;

    @Column(name = "totalTime")
    private Double totalTime;

    @Column(name = "secondPerQuestion")
    private Double secondPerQuestion;

    @Column(name = "score_percent")
    private Double scorePercent;

    @Column(name = "is_passed")
    private Boolean isPassed;

    @OneToMany(mappedBy = "userTest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    List<UserAnswer> userAnswers;
}
