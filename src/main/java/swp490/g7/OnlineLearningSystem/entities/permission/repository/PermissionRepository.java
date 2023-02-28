package swp490.g7.OnlineLearningSystem.entities.permission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.permission.domain.Permission;
import swp490.g7.OnlineLearningSystem.entities.permission.domain.PermissionId;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, PermissionId>, PermissionRepositoryCustom {
}
