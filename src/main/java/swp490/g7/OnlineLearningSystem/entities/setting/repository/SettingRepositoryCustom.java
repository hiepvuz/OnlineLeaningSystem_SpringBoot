package swp490.g7.OnlineLearningSystem.entities.setting.repository;

import swp490.g7.OnlineLearningSystem.entities.setting.domain.response.SettingResponseDto;

import java.util.List;

public interface SettingRepositoryCustom {
    List<SettingResponseDto> filter(Long typeId, Boolean status, String settingTitle);
}
