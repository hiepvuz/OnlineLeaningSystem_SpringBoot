package swp490.g7.OnlineLearningSystem.entities.subject_setting.repository;

import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.response.SubjectSettingResponseDto;

import java.util.List;

public interface SubjectSettingRepositoryCustom {
    List<SubjectSettingResponseDto> filter(Long subjectId, Long typeId, Boolean status, String settingTitle,
                                           String subjectCode);

    List<SubjectSettingResponseDto> getAllBySubjectModuleOrSubjectContent(Long subjectId, Long typeId);
}
