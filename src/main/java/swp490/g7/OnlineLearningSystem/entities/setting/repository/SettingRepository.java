package swp490.g7.OnlineLearningSystem.entities.setting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.setting.domain.Setting;

import java.util.List;

@Repository
public interface SettingRepository extends JpaRepository<Setting, Long>, SettingRepositoryCustom {
    List<Setting> findByTypeId(Long typeId);

    Setting findBySettingTitle(String title);

    Setting findByDisplayOrderAndTypeId(String displayOrder, Long typeId);
}

