package swp490.g7.OnlineLearningSystem.entities.user_package.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.user_package.domain.UserPackage;

import java.util.List;

@Repository
public interface UserPackageRepository extends JpaRepository<UserPackage, Long> {

    @Query("SELECT p.packId FROM UserPackage p WHERE p.userId = ?1")
    List<Long> findListPackageIdByUserId(Long userId);
}
