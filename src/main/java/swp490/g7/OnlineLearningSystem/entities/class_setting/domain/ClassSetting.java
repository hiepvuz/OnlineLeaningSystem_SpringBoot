package swp490.g7.OnlineLearningSystem.entities.class_setting.domain;


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
@Table(name = "class_setting")
public class ClassSetting {

    public static final Long CLASS_MODULE = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "class_setting_id")
    private Long classSettingId;

    @Column(name = "type_id")
    Long typeId;

    @Column(name = "class_setting_title")
    String classSettingTitle;

    @Column(name = "setting_value")
    String settingValue;

    @Column(name = "display_order")
    String displayOrder;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "status")
    Boolean status;

    @Column(name = "class_id")
    Long classId;
}
