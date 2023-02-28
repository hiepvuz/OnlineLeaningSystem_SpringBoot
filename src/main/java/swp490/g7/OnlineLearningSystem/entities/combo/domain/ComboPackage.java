package swp490.g7.OnlineLearningSystem.entities.combo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.Pack;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "combo_package")
public class ComboPackage {

    @EmbeddedId
    private ComboPackageId comboPackageId;

    @ManyToOne()
    @MapsId(value = "comboId")
    private Combo combo;

    @ManyToOne
    @MapsId(value = "packId")
    private Pack pack;
}
