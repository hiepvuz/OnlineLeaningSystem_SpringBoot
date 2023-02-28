package swp490.g7.OnlineLearningSystem.entities.class_setting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.class_setting.domain.ClassSetting;


@Repository
public interface ClassSettingRepository extends JpaRepository<ClassSetting, Long>, ClassSettingRepositoryCustom {
    @Query("SELECT cs.status FROM ClassSetting cs WHERE cs.classSettingId = ?1")
    Boolean getStatusByClassSettingId(Long classSettingId);
}
