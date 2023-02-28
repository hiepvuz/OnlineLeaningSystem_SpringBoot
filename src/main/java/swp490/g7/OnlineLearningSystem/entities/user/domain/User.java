package swp490.g7.OnlineLearningSystem.entities.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.ClassUser;
import swp490.g7.OnlineLearningSystem.entities.setting.domain.Setting;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
public class User {
    public static final String ID = "user_id";
    public static final String DATE_OF_BIRTH = "date_of_birth";
    public static final String EMAIL = "email";
    public static final String USER_NAME = "user_name";
    public static final String GENDER = "gender";
    public static final String IMAGE = "image";
    public static final String PASSWORD = "password";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String CREATED_DATE = "created_date";
    public static final String UPDATED_DATE = "updated_date";
    public static final String DISABLE = "disable";
    public static final String DISABLE_TRUE = "true";
    public static final String DISABLE_FALSE = "false";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    Long userId;

    @Column(name = "email", unique = true)
    String email;

    @Column(name = "full_name")
    String fullName;

    @Column(name = "address")
    String address;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "dateOfBirth")
    Date dateOfBirth;

    @Column(name = "username", unique = true, nullable = false)
    String username;

    @Column(name = "gender")
    String gender;

    @Column(name = "avatar_url")
    String avatarUrl;

    @Column(name = "password")
    String password;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "disabled")
    Boolean disabled = true;

    @Column(name = "verify_code", length = 64)
    String verifyCode;

    @Column(name = "verify")
    Boolean verify = false;

    @Column(name = "note")
    String note;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_date", columnDefinition = "TIMESTAMP")
    Date createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "updated_date", columnDefinition = "TIMESTAMP")
    Date updatedDate;

    @Column(name = "google_auth")
    Boolean googleAuth;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Setting> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    List<ClassUser> classUsers;
}