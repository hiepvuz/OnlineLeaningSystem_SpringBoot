package swp490.g7.OnlineLearningSystem.entities.classroom.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.ClassLesson;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "classroom")
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "class_id")
    Long classId;

    @Column(name = "class_code")
    String classCode;

    @Column(name = "from_date")
    Date fromDate;

    @Column(name = "to_date")
    Date toDate;

    @Column(name = "trainer_id")
    Long trainerId;

    @Column(name = "supporter_id")
    Long supporterId;

    @Column(name = "status")
    Boolean status;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "branch")
    String branch;

    @Column(name = "term")
    String term;

    @Column(name = "subject_id")
    Long subjectId;

    @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    List<ClassUser> classUsers;

    @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    List<ClassLesson> classLessons;
}
