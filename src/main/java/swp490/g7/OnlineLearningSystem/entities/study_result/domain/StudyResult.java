package swp490.g7.OnlineLearningSystem.entities.study_result.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "study_result")
public class StudyResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "study_result_id")
    Long studyResultId;

    @Column(name = "subject_id")
    Long subjectId;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "lesson_id")
    Long lessonId;

    @Column(name = "class_id")
    Long classId;

    @Column(name = "class_lesson_id")
    Long classLessonId;

    @Column(name = "is_completed")
    Boolean isCompleted;

    @Column(name = "lesson_result")
    Double lessonResult;

    @Column(name = "is_passed")
    Boolean isPassed;
}
