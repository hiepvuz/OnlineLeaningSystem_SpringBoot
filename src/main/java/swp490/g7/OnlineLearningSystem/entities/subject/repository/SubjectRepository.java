package swp490.g7.OnlineLearningSystem.entities.subject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;

import java.util.List;
import java.util.Set;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>, SubjectRepositoryCustom {

    @Query(value = "select u.user_id, u.username from users u inner join user_role ur on u.user_id = ur.user_id inner join setting s on ur.role_id = s.setting_id where ur.role_id = 23 and u.disabled = 0", nativeQuery = true)
    Set<Object> getAllExpert();

    @Query(value = "select u.user_id, u.username from users u inner join user_role ur on u.user_id = ur.user_id inner join setting s on ur.role_id = s.setting_id where ur.role_id = 22 and u.disabled = 0", nativeQuery = true)
    Set<Object> getAllManager();

    @Query(value = "select s.subject_code from subject s", nativeQuery = true)
    Set<String> getAllSubjectCode();

    List<Subject> findBySubjectIdInAndStatusIsTrue(List<Long> subjectIds);

    List<Subject> findAll();

    List<Subject> findByManagerIdAndStatusIsTrue(Long managerId);

    List<Subject> findByExpertIdAndStatusIsTrue(Long expertId);

    List<Subject> findAllByStatusIsTrue();
}
