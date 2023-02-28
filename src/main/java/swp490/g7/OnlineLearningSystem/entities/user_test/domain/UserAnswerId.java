package swp490.g7.OnlineLearningSystem.entities.user_test.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserAnswerId implements Serializable {
    @Column(name = "user_test_id", nullable = false)
    private Long userTestId;

    @Column(name = "question_id", nullable = false)
    private Long questionId;
}
