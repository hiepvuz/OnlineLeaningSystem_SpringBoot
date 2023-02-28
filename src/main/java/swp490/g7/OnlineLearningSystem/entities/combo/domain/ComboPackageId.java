package swp490.g7.OnlineLearningSystem.entities.combo.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class ComboPackageId implements Serializable {
    @Column(name = "combo_id", nullable = false)
    private Long comboId;

    @Column(name = "package_id", nullable = false)
    private Long packId;
}
