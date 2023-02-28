package swp490.g7.OnlineLearningSystem.entities.permission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swp490.g7.OnlineLearningSystem.entities.permission.domain.PermissionId;
import swp490.g7.OnlineLearningSystem.entities.permission.domain.request.PermissionRequestDto;
import swp490.g7.OnlineLearningSystem.entities.permission.domain.response.PermissionResponseDto;
import swp490.g7.OnlineLearningSystem.entities.permission.service.PermissionService;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("")
    public PermissionResponseDto getById(@RequestBody PermissionId id) {
        return permissionService.getById(id);
    }

    @PostMapping("")
    public PermissionResponseDto create(@Valid @RequestBody PermissionRequestDto request) {
        return permissionService.create(request);
    }

    @PutMapping("")
    public PermissionResponseDto update(@Valid @RequestBody PermissionRequestDto request) {
        return permissionService.update(request);
    }

    @DeleteMapping("")
    public void delete(@RequestBody PermissionId id) {
        permissionService.delete(id);
    }

    @GetMapping("/all")
    public PaginationResponse getAll(Pageable pageable) {
        return permissionService.getAll(pageable);
    }

    @GetMapping("/{roleId}/role")
    public List<PermissionResponseDto> getByRole(@PathVariable Long roleId) {
        return permissionService.getByRole(roleId);
    }

    @GetMapping("/{screenId}/screen")
    public List<PermissionResponseDto> getByScreen(@PathVariable Long screenId) {
        return permissionService.getByScreen(screenId);
    }
}
