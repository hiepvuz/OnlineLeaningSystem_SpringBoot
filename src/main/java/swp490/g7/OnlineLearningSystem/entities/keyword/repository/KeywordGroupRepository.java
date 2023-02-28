package swp490.g7.OnlineLearningSystem.entities.keyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.KeywordGroup;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.KeywordGroupId;

@Repository
public interface KeywordGroupRepository extends JpaRepository<KeywordGroup, KeywordGroupId> {
}
