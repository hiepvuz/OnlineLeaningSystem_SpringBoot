package swp490.g7.OnlineLearningSystem.entities.user_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.user_test.domain.TestAnalysis;

import java.util.List;

@Repository
public interface TestAnalysisRepository extends JpaRepository<TestAnalysis, Long> {
    List<TestAnalysis> findAllByUserTestId(Long userTestId);
}
