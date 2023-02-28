package swp490.g7.OnlineLearningSystem.entities.subject_setting.service;

import org.springframework.data.domain.Pageable;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.SubjectSetting;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.request.SubjectSettingRequestDto;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.response.SubjectSettingResponseDto;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.List;
import java.util.Optional;

public interface SubjectSettingService {
    Optional<SubjectSetting> findById(Long id);

    SubjectSettingResponseDto save(SubjectSettingRequestDto request);

    SubjectSettingResponseDto update(Long id, SubjectSettingRequestDto request);

    void enable(Long id);

    void disable(Long id);

    PaginationResponse filter(Long subjectId, Long typeId, Boolean status, String settingTitle, String subjectCode, Pageable pageable);

    PaginationResponse getSubjectModule(Long subjectId, Pageable pageable);

    List<SubjectSettingResponseDto> getAllBySubjectModuleOrSubjectContent(Long subjectId, Long typeId);
}
