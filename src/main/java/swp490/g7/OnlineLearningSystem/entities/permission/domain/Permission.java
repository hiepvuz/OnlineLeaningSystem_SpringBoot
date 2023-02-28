package swp490.g7.OnlineLearningSystem.entities.permission.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "permission")
public class Permission {

    @EmbeddedId
    PermissionId permissionId;

    @Column(name = "all_data")
    Boolean allData;

    @Column(name = "can_delete")
    Boolean canDelete;

    @Column(name = "can_add")
    Boolean canAdd;

    @Column(name = "can_edit")
    Boolean canEdit;
}
