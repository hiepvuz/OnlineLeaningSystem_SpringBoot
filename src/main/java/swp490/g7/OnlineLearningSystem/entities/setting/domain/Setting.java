package swp490.g7.OnlineLearningSystem.entities.setting.domain;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "setting")
public class Setting implements Serializable {

    public static final String USER_ROLE_ADMIN = "Admin";
    public static final String USER_ROLE_MANAGER = "Manager";
    public static final String USER_ROLE_EXPERT = "Expert";
    public static final String USER_ROLE_TRAINEE = "Trainee";
    public static final String USER_ROLE_TRAINER = "Trainer";
    public static final String USER_ROLE_SUPPORTER = "Supporter";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "setting_id")
    Long settingId;

    @Column(name = "type_id")
    @Nullable
    Long typeId;

    @Column(name = "setting_title")
    String settingTitle;

    @Column(name = "setting_value", columnDefinition = "TEXT")
    String settingValue;

    @Column(name = "display_order")
    String displayOrder;

    @Column(name = "status")
    Boolean status;

    @Column(name = "description", columnDefinition = "TEXT")
    String description;
}