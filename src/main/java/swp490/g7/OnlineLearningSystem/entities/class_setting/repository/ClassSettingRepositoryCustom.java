package swp490.g7.OnlineLearningSystem.entities.class_setting.repository;

import swp490.g7.OnlineLearningSystem.entities.class_setting.domain.response.ClassSettingResponseDto;

import java.util.List;

public interface ClassSettingRepositoryCustom {
    List<ClassSettingResponseDto> filter(Long subjectId, Long typeId, Boolean status, String settingTitle, String classCode);
}
