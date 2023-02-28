package swp490.g7.OnlineLearningSystem.entities.permission.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class PermissionId implements Serializable {
    @Column(name = "screen_id", nullable = false)
    private Long screenId;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    public PermissionId(Long screenId, Long roleId) {
        this.screenId = screenId;
        this.roleId = roleId;
    }
}
