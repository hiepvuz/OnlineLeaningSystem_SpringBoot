package swp490.g7.OnlineLearningSystem.entities.classroom.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swp490.g7.OnlineLearningSystem.entities.user.domain.User;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "class_user")
public class ClassUser {
    @EmbeddedId
    private ClassUserId id = new ClassUserId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "classId")
    @JoinColumn(name = "class_id")
    @JsonIgnore
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;
}
