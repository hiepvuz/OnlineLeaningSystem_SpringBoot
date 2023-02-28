package swp490.g7.OnlineLearningSystem.entities.class_setting.service;

import org.springframework.data.domain.Pageable;
import swp490.g7.OnlineLearningSystem.entities.class_setting.domain.request.ClassSettingRequestDto;
import swp490.g7.OnlineLearningSystem.entities.class_setting.domain.response.ClassSettingResponseDto;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.List;

public interface ClassSettingService {
    PaginationResponse findAll(Pageable pageable);

    ClassSettingResponseDto findById(Long id);

    void enable(Long id);

    void disable(Long id);

    PaginationResponse filter(Long subjectId, Long typeId, Boolean status, String settingTitle, String classCode,
                              Pageable pageable);

    ClassSettingResponseDto save(ClassSettingRequestDto request);

    ClassSettingResponseDto update(Long id, ClassSettingRequestDto request);

    List<ClassSettingResponseDto> getAllByClassModule(Long subjectId);
}
