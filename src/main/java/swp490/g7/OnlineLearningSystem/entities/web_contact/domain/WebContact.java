package swp490.g7.OnlineLearningSystem.entities.web_contact.domain;

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
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "web_contact")
public class WebContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "response")
    private String response;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "setting_id")
    private Long settingId;

    @Column(name = "status")
    private Boolean status;

}
