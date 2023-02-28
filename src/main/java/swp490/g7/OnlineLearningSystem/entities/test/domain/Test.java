package swp490.g7.OnlineLearningSystem.entities.test.domain;

import lombok.Data;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;
import swp490.g7.OnlineLearningSystem.entities.user_test.domain.UserTest;

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
import java.util.List;

@Data
@Entity
@Table(name = "test")
public class Test {
    public static final Integer DEMO_TEST = 1;

    public static final Integer MOCK_TEST = 2;

    public static final Integer FULL_TEST = 3;

    public static final Integer QUIZ_LESSON = 4;

    public static final Integer PRACTICE_TEST = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "test_id")
    Long testId;

    @Column(name = "test_type")
    Integer testType;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    List<TestConfig> testConfig;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    Subject subject;

    @Column(name = "name")
    String name;

    @Column(name = "status")
    Boolean status;

    @Column(name = "duration")
    Integer duration;

    @Column(name = "pass_rate")
    Integer passRate;

    @Column(name = "source_id")
    Long sourceId;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @OneToMany(mappedBy = "test", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserTest> userTests;
}
