package swp490.g7.OnlineLearningSystem.entities.user_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.test.domain.Test;
import swp490.g7.OnlineLearningSystem.entities.user_test.domain.UserTest;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTestRepository extends JpaRepository<UserTest, Long>, UserTestRepositoryCustom {
    @Query("SELECT ut FROM UserTest ut LEFT JOIN Test t ON ut.test.testId = t.testId" +
            " WHERE t.testType = 5 AND t.subject.subjectId = ?1 AND ut.userId = ?2" +
            " AND t.name LIKE %?3%")
    List<UserTest> findByTestPracticeAndUserId(Long subjectId, Long userId, String name);

//    Optional<UserTest> findByTestAndUserId(Test test, Long userId);

    Optional<UserTest> findByTestAndUserIdAndClassLessonIdIsNull(Test test, Long userId);

    Optional<UserTest> findByTestAndUserIdAndLessonIdIsNull(Test test, Long userId);
}
