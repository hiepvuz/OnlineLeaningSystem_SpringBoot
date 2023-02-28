package swp490.g7.OnlineLearningSystem.entities.lesson.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lesson")
public class Lesson {

    public static final String LESSON_TYPE_NORMAL = "NORMAL_LESSON";

    public static final String LESSON_TYPE_QUIZ = "QUIZ_LESSON";

    public static final int LESSON_QUIZ_TYPE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @Column(name = "module_id")
    private Long moduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "test_id")
    private Long testId;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "type_lesson")
    private String typeLesson;

    @Column(name = "video_url", columnDefinition = "TEXT")
    private String videoUrl;

    @Column(name = "lesson_text", columnDefinition = "TEXT")
    private String lessonText;

    @Column(name = "updated_order")
    private Date updatedOrder;

    @Column(name = "attachment_url")
    private String attachmentUrl;

    @Column(name = "duration")
    private Integer duration;
}
