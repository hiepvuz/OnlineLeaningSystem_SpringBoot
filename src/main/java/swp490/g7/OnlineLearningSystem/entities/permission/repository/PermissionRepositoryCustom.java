package swp490.g7.OnlineLearningSystem.entities.permission.repository;

import swp490.g7.OnlineLearningSystem.entities.permission.domain.response.PermissionResponseDto;

import java.util.List;

public interface PermissionRepositoryCustom {

    List<PermissionResponseDto> getAll();

    List<PermissionResponseDto> getByRole(Long roleId);

    List<PermissionResponseDto> getByScreen(Long screenId);
}
