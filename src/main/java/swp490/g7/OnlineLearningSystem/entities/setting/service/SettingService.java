package swp490.g7.OnlineLearningSystem.entities.setting.service;

import org.springframework.data.domain.Pageable;
import swp490.g7.OnlineLearningSystem.entities.setting.domain.request.SettingRequestDto;
import swp490.g7.OnlineLearningSystem.entities.setting.domain.response.SettingResponseDto;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.Set;

public interface SettingService {
    PaginationResponse findByTypeId(Long typeId, Pageable pageable);

    PaginationResponse findAll(Pageable pageable);

    SettingResponseDto create(SettingRequestDto settingRequestDto);

    SettingResponseDto update(Long id, SettingRequestDto settingRequestDto);

    SettingResponseDto findById(Long id);

    Set<String> findAllRole();

    PaginationResponse filter(Long typeId, Boolean status, String settingTitle, Pageable pageable);

    void enable(Long id);

    void disable(Long id);
}
