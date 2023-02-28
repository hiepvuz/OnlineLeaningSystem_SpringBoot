package swp490.g7.OnlineLearningSystem.entities.user_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.user_test.domain.UserAnswer;
import swp490.g7.OnlineLearningSystem.entities.user_test.domain.UserAnswerId;
import swp490.g7.OnlineLearningSystem.entities.user_test.domain.UserTest;

import java.util.List;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, UserAnswerId> {
    List<UserAnswer> findAllByUserTest(UserTest userTest);
}
