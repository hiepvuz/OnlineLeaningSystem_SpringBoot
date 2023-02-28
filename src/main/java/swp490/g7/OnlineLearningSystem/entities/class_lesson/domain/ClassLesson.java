package swp490.g7.OnlineLearningSystem.entities.class_lesson.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.Classroom;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;

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
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "class_lesson")
public class ClassLesson {

    public static final Integer CLASS_MODULE = 1;

    public static final String CLASS_LESSON_TYPE_NORMAL = "NORMAL_LESSON";

    public static final String CLASS_LESSON_TYPE_QUIZ = "QUIZ_LESSON";

    public static final String SUBJECT_LESSON_TYPE = "SUBJECT_LESSON";

    public static final int CLASS_LESSON_QUIZ_TYPE = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "class_lesson_id")
    private Long classLessonId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id")
    private Classroom classroom;

    @Column(name = "name")
    private String name;

    @Column(name = "module_id")
    private Long moduleId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id")
    private Subject subject;

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

    @Column(name = "attachment_url", columnDefinition = "TEXT")
    private String attachmentUrl;

    @Column(name = "updated_order")
    private Date updatedOrder;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "test_id")
    private Long testId;
}
