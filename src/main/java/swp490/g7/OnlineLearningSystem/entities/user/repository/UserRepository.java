package swp490.g7.OnlineLearningSystem.entities.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import swp490.g7.OnlineLearningSystem.entities.user.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom, PagingAndSortingRepository<User, Long> {
    Page<User> findAllByUsernameContains(String userName, Pageable pageable);

    Page<User> findByDisabledTrue(Pageable pageable);

    Page<User> findByDisabledFalse(Pageable pageable);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query(value = "select s.setting_title from users u inner join user_role ur on u.user_id = ur.user_id inner join setting s on ur.role_id = s.setting_id where u.user_id = ?1", nativeQuery = true)
    Set<Object> getRole(Long userId);

    @Query(value = "select u.user_id, u.username from users u inner join user_role ur on u.user_id = ur.user_id inner join setting s on ur.role_id = s.setting_id where ur.role_id = ?1", nativeQuery = true)
    List<Object> findByRole(Long roleId);

    List<User> findByEmailIn(List<String> emails);

    @Query("SELECT u.userId FROM User u WHERE u.username = ?1")
    Long getIdByUserName(String userName);

    @Query("SELECT u.username FROM User u WHERE u.userId = ?1")
    String getUsernameByUserId(Long userId);

    @Query(value = "select u.user_id, u.username from users u inner join user_role ur on u.user_id = ur.user_id inner join setting s on ur.role_id = s.setting_id where ur.role_id = 26 and u.disabled = 0", nativeQuery = true)
    Set<Object> getAllSupporter();

    @Query(value = "select u.user_id, u.username from users u inner join user_role ur on u.user_id = ur.user_id inner join setting s on ur.role_id = s.setting_id where ur.role_id = 25 and u.disabled = 0", nativeQuery = true)
    Set<Object> getAllTrainer();

    @Query(value = "select email from users", nativeQuery = true)
    Set<String> getEmails();
}

