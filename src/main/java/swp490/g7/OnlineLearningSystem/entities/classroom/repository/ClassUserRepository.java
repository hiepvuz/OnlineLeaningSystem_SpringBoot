package swp490.g7.OnlineLearningSystem.entities.classroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.ClassUser;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.ClassUserId;

@Repository
public interface ClassUserRepository extends JpaRepository<ClassUser, ClassUserId> {
}
