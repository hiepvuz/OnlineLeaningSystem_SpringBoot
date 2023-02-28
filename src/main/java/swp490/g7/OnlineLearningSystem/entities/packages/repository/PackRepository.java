package swp490.g7.OnlineLearningSystem.entities.packages.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.Pack;

import java.util.List;

@Repository
public interface PackRepository extends JpaRepository<Pack, Long> {
    @Override
    List<Pack> findAll();

    List<Pack> findAllBySubjectId(Long subjectId);

    List<Pack> findAllByPackageIdInAndSubjectId(List<Long> packageIds, Long subjectId);
}
