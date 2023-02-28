package swp490.g7.OnlineLearningSystem.entities.subject_setting.domain;

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

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "subject_setting")
public class SubjectSetting {

    public static final Long SUBJECT_MODULE = 1L;

    public static final Long SUBJECT_KEYWORD_CATEGORIES = 2L;

    public static final Long SUBJECT_CONTENT_GROUP_TYPES = 3L;

    public static final Long SUBJECT_SLOT = 4L;

    public static final Long COMPLEXITY_LEVELS = 5L;

    public static final Long QUALITY_LEVELS = 6L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subject_setting_id")
    Long subjectSettingId;

    @Column(name = "subject_setting_title")
    String subjectSettingTitle;

    @Column(name = "type_id")
    Long typeId;

    @Column(name = "setting_value")
    String settingValue;

    @Column(name = "display_order")
    String displayOrder;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;

    @Column(name = "status")
    Boolean status;

    @Column(name = "subject_id")
    Long subjectId;
}
