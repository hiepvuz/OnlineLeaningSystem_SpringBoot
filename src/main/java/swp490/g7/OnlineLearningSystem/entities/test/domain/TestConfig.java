package swp490.g7.OnlineLearningSystem.entities.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "test_config")
public class TestConfig {

    public static final String LESSON_CONFIG_TYPE = "LESSON_CONFIG";

    public static final String GROUP_CONFIG_TYPE = "GROUP_CONFIG";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    Test test;

    @Column(name = "group_id")
    Long groupId;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "lesson_id")
    Long lessonId;

    @Column(name = "class_lesson_id")
    Long classLessonId;
}
