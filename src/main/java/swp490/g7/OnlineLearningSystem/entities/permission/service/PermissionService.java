package swp490.g7.OnlineLearningSystem.entities.permission.service;

import org.springframework.data.domain.Pageable;
import swp490.g7.OnlineLearningSystem.entities.permission.domain.PermissionId;
import swp490.g7.OnlineLearningSystem.entities.permission.domain.request.PermissionRequestDto;
import swp490.g7.OnlineLearningSystem.entities.permission.domain.response.PermissionResponseDto;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.List;

public interface PermissionService {

    PermissionResponseDto getById(PermissionId id);

    PermissionResponseDto update(PermissionRequestDto request);

    PermissionResponseDto create(PermissionRequestDto request);

    void delete(PermissionId id);

    PaginationResponse getAll(Pageable pageable);

    List<PermissionResponseDto> getByRole(Long roleId);

    List<PermissionResponseDto> getByScreen(Long screenId);
}
