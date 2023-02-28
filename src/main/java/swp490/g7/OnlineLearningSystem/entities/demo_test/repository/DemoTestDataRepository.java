package swp490.g7.OnlineLearningSystem.entities.demo_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.demo_test.domain.DemoTest;

@Repository
public interface DemoTestDataRepository extends JpaRepository<DemoTest, Long> {
}
