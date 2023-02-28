package swp490.g7.OnlineLearningSystem.entities.permission.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp490.g7.OnlineLearningSystem.entities.permission.domain.Permission;
import swp490.g7.OnlineLearningSystem.entities.permission.domain.PermissionId;
import swp490.g7.OnlineLearningSystem.entities.permission.domain.request.PermissionRequestDto;
import swp490.g7.OnlineLearningSystem.entities.permission.domain.response.PermissionResponseDto;
import swp490.g7.OnlineLearningSystem.entities.permission.repository.PermissionRepository;
import swp490.g7.OnlineLearningSystem.entities.permission.service.PermissionService;
import swp490.g7.OnlineLearningSystem.entities.setting.domain.Setting;
import swp490.g7.OnlineLearningSystem.entities.setting.repository.SettingRepository;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionServiceImpl implements PermissionService {
    private static final Logger logger = LogManager.getLogger(PermissionServiceImpl.class);

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Override
    public PermissionResponseDto getById(PermissionId id) {
        Optional<Permission> permission = permissionRepository.findById(id);
        if (!permission.isPresent()) {
            logger.error("Setting not found with setting id: {}", id);
            throw new OnlineLearningException(ErrorTypes.PERMISSION_NOT_FOUND, id.toString());
        }
        return convertToPermissionResponseDto(permission.get());
    }

    //    @Caching(evict = {
//            @CacheEvict(value = "perByRole", allEntries = true),
//            @CacheEvict(value = "perByScreen", allEntries = true)
//    })
    @Override
    public PermissionResponseDto update(PermissionRequestDto request) {
        logger.info("Start updating permission");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Permission must not be empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        PermissionId id = new PermissionId(request.getScreenId(), request.getRoleId());
        Optional<Permission> existPermission = permissionRepository.findById(id);
        if (!existPermission.isPresent()) {
            logger.error("Permission not found");
            throw new OnlineLearningException(ErrorTypes.PERMISSION_NOT_FOUND);
        }
        existPermission.get().setAllData(request.getAllData());
        existPermission.get().setCanAdd(request.getCanAdd());
        existPermission.get().setCanDelete(request.getCanDelete());
        existPermission.get().setCanEdit(request.getCanEdit());
        permissionRepository.save(existPermission.get());
        logger.info("Successfully update permission");
        return convertToPermissionResponseDto(existPermission.get());
    }

    //    @Caching(evict = {
//            @CacheEvict(value = "perByRole", allEntries = true),
//            @CacheEvict(value = "perByScreen", allEntries = true)
//    })
    @Override
    public PermissionResponseDto create(PermissionRequestDto request) {
        logger.info("Start creating permission");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Permission must not be empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        Optional<Setting> existSetting = settingRepository.findById(request.getRoleId());
        if (!existSetting.isPresent()) {
            logger.error("Role not found with roleId: {}", request.getRoleId());
            throw new OnlineLearningException(ErrorTypes.ROLE_NOT_FOUND);
        }
        PermissionId id = new PermissionId(request.getScreenId(), request.getRoleId());
        Optional<Permission> existPermission = permissionRepository.findById(id);
        if (existPermission.isPresent()) {
            logger.error("Permission already exist");
            throw new OnlineLearningException(ErrorTypes.PERMISSION_ALREADY_EXIST);
        }
        Permission permission = new Permission();
        permission.setPermissionId(id);
        permission.setCanEdit(request.getCanEdit());
        permission.setCanAdd(request.getCanAdd());
        permission.setAllData(request.getAllData());
        permission.setCanDelete(request.getCanDelete());
        permissionRepository.save(permission);
        logger.info("Successfully create permission");
        return convertToPermissionResponseDto(permission);
    }

    //    @Caching(evict = {
//            @CacheEvict(value = "perByRole", allEntries = true),
//            @CacheEvict(value = "perByScreen", allEntries = true)
//    })
    @Override
    public void delete(PermissionId id) {
        logger.info("Start deleting permission");
        Optional<Permission> existPermission = permissionRepository.findById(id);
        if (!existPermission.isPresent()) {
            logger.error("Permission not found");
            throw new OnlineLearningException(ErrorTypes.PERMISSION_NOT_FOUND);
        }
        permissionRepository.delete(existPermission.get());
        logger.info("Successfully delete permission");
    }

    @Override
    public PaginationResponse getAll(Pageable pageable) {
        PagedListHolder pagedListHolder =
                new PagedListHolder(permissionRepository.getAll());
        pagedListHolder.setPage(pageable.getPageNumber());
        pagedListHolder.setPageSize(pageable.getPageSize());

        return PaginationResponse.builder()
                .total(pagedListHolder.getSource().size())
                .numberOfPage(pagedListHolder.getPageCount())
                .pageIndex(pageable.getPageNumber())
                .items(pagedListHolder.getPageList())
                .build();
    }

    //    @Cacheable("perByRole")
    @Override
    public List<PermissionResponseDto> getByRole(Long roleId) {
        return permissionRepository.getByRole(roleId);
    }

    //    @Cacheable("perByScreen")
    @Override
    public List<PermissionResponseDto> getByScreen(Long screenId) {
        return permissionRepository.getByScreen(screenId);
    }

    private PermissionResponseDto convertToPermissionResponseDto(Permission permission) {
        PermissionResponseDto permissionDto = new PermissionResponseDto();
        Optional<Setting> settingRole = settingRepository.findById(permission.getPermissionId().getRoleId());
        Optional<Setting> settingScreen = settingRepository.findById(permission.getPermissionId().getScreenId());
        permissionDto.setAllData(permission.getAllData());
        permissionDto.setCanAdd(permission.getCanAdd());
        permissionDto.setCanDelete(permission.getCanDelete());
        permissionDto.setCanEdit(permission.getCanEdit());
        permissionDto.setRoleId(settingRole.get().getSettingId());
        permissionDto.setRoleName(settingRole.get().getSettingTitle());
        permissionDto.setScreenId(settingScreen.get().getSettingId());
        permissionDto.setScreenName(settingScreen.get().getSettingTitle());
        return permissionDto;
    }
}
