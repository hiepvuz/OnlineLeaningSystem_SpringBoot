package swp490.g7.OnlineLearningSystem.entities.setting.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp490.g7.OnlineLearningSystem.entities.setting.domain.Setting;
import swp490.g7.OnlineLearningSystem.entities.setting.domain.request.SettingRequestDto;
import swp490.g7.OnlineLearningSystem.entities.setting.domain.response.SettingResponseDto;
import swp490.g7.OnlineLearningSystem.entities.setting.repository.SettingRepository;
import swp490.g7.OnlineLearningSystem.entities.setting.service.SettingService;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.BeanUtility;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SettingServiceImpl implements SettingService {
    private static final Logger logger = LogManager.getLogger(SettingServiceImpl.class);

    @Autowired
    private SettingRepository settingRepository;

    @Override
    public PaginationResponse findByTypeId(Long typeId, Pageable pageable) {
        if (ObjectUtils.isEmpty(typeId)) {
            logger.error("Can not found role with type id: null");
            throw new OnlineLearningException(ErrorTypes.ROLE_NOT_FOUND);
        }
        PagedListHolder pagedListHolder = new PagedListHolder(settingRepository.findByTypeId(typeId));
        pagedListHolder.setPage(pageable.getPageNumber());
        pagedListHolder.setPageSize(pageable.getPageSize());

        return PaginationResponse.builder()
                .total(pagedListHolder.getSource().size())
                .numberOfPage(pagedListHolder.getPageCount())
                .pageIndex(pageable.getPageNumber())
                .items(pagedListHolder.getPageList())
                .build();
    }

    @Override
    public PaginationResponse findAll(Pageable pageable) {
        PagedListHolder pagedListHolder = new PagedListHolder(settingRepository.findAll());
        pagedListHolder.setPage(pageable.getPageNumber());
        pagedListHolder.setPageSize(pageable.getPageSize());

        return PaginationResponse.builder()
                .total(pagedListHolder.getSource().size())
                .numberOfPage(pagedListHolder.getPageCount())
                .pageIndex(pageable.getPageNumber())
                .items(pagedListHolder.getPageList())
                .build();
    }


    @Override
    public SettingResponseDto create(SettingRequestDto request) {
        logger.info("Start creating setting");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Setting must not be empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        Setting setting = BeanUtility.convertValue(request, Setting.class);
        settingRepository.save(setting);
        logger.info("Creating setting successfully");
        return BeanUtility.convertValue(setting, SettingResponseDto.class);
    }

    @Override
    public SettingResponseDto update(Long id, SettingRequestDto request) {
        logger.info("Start updating setting");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Setting must not be empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        Optional<Setting> existSetting = settingRepository.findById(id);
        if (!existSetting.isPresent()) {
            logger.error("Setting not found with setting id: {}", id);
            throw new OnlineLearningException(ErrorTypes.SETTING_NOT_FOUND, id.toString());
        }
        Setting setting = existSetting.get();
        if (ObjectUtils.isNotEmpty(request.getTypeId())) {
            setting.setTypeId(request.getTypeId());
        }
        setting.setSettingTitle(request.getSettingTitle());
        setting.setSettingValue(request.getSettingValue());
        setting.setDescription(request.getDescription());
        setting.setDisplayOrder(request.getDisplayOrder());
        setting.setStatus(request.getStatus());
        settingRepository.save(setting);
        logger.info("Updating setting successfully");
        return BeanUtility.convertValue(setting, SettingResponseDto.class);
    }

    @Override
    public SettingResponseDto findById(Long id) {
        Optional<Setting> setting = settingRepository.findById(id);
        if (!setting.isPresent()) {
            logger.error("Setting not found with setting id: {}", id);
            throw new OnlineLearningException(ErrorTypes.SETTING_NOT_FOUND, id.toString());
        }
        return BeanUtility.convertValue(setting.get(), SettingResponseDto.class);
    }


    @Override
    public Set<String> findAllRole() {
        List<Setting> listSettingTypeRole = settingRepository.findByTypeId(1L);
        Set<String> allRole = new HashSet<>();
        listSettingTypeRole.forEach(role -> {
            allRole.add(role.getSettingTitle());
        });
        return allRole;
    }

    @Override
    public PaginationResponse filter(Long typeId, Boolean status, String settingTitle, Pageable pageable) {
        PagedListHolder pagedListHolder = new PagedListHolder(settingRepository.filter(typeId, status, settingTitle));
        pagedListHolder.setPage(pageable.getPageNumber());
        pagedListHolder.setPageSize(pageable.getPageSize());

        return PaginationResponse.builder()
                .total(pagedListHolder.getSource().size())
                .numberOfPage(pagedListHolder.getPageCount())
                .pageIndex(pageable.getPageNumber())
                .items(pagedListHolder.getPageList())
                .build();
    }

    @Override
    public void enable(Long id) {
        logger.info("Starting enable classroom with id {}", id);
        Optional<Setting> setting = settingRepository.findById(id);
        if (!setting.isPresent()) {
            logger.error("Can not found user with id {}", id);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND);
        }
        setting.get().setStatus(true);
        settingRepository.save(setting.get());
        logger.info("Successfully enable user with id {}", id);
    }

    @Override
    public void disable(Long id) {

        logger.info("Starting enable classroom with id {}", id);
        Optional<Setting> setting = settingRepository.findById(id);
        if (!setting.isPresent()) {
            logger.error("Can not found user with id {}", id);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND);
        }
        setting.get().setStatus(false);
        settingRepository.save(setting.get());
        logger.info("Successfully enable user with id {}", id);
    }


}
