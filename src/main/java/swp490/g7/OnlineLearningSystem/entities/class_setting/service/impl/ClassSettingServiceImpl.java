package swp490.g7.OnlineLearningSystem.entities.class_setting.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp490.g7.OnlineLearningSystem.entities.class_setting.domain.ClassSetting;
import swp490.g7.OnlineLearningSystem.entities.class_setting.domain.request.ClassSettingRequestDto;
import swp490.g7.OnlineLearningSystem.entities.class_setting.domain.response.ClassSettingResponseDto;
import swp490.g7.OnlineLearningSystem.entities.class_setting.repository.ClassSettingRepository;
import swp490.g7.OnlineLearningSystem.entities.class_setting.service.ClassSettingService;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.Classroom;
import swp490.g7.OnlineLearningSystem.entities.classroom.repository.ClassroomRepository;
import swp490.g7.OnlineLearningSystem.entities.setting.service.impl.SettingServiceImpl;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.BeanUtility;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.List;
import java.util.Optional;

@Service
public class ClassSettingServiceImpl implements ClassSettingService {
    private static final Logger logger = LogManager.getLogger(SettingServiceImpl.class);

    @Autowired
    ClassSettingRepository classSettingRepository;

    @Autowired
    ClassroomRepository classroomRepository;

    @Override
    public ClassSettingResponseDto findById(Long id) {
        Optional<ClassSetting> setting = classSettingRepository.findById(id);
        if (!setting.isPresent()) {
            logger.error("Setting not found with setting id: {}", id);
            throw new OnlineLearningException(ErrorTypes.CLASS_SETTING_NOT_FOUND, id.toString());
        }
        return BeanUtility.convertValue(setting.get(), ClassSettingResponseDto.class);
    }

    @Override
    public PaginationResponse findAll(Pageable pageable) {
        PagedListHolder pagedListHolder = new PagedListHolder(classSettingRepository.findAll());

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
        logger.info("Starting enable class setting with id {}", id);
        Optional<ClassSetting> classSetting = classSettingRepository.findById(id);
        if (!classSetting.isPresent()) {
            logger.error("Can not found class setting with id {}", id);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND);
        }
        classSetting.get().setStatus(true);
        classSettingRepository.save(classSetting.get());
        logger.info("Successfully enable class setting with id {}", id);
    }

    @Override
    public void disable(Long id) {

        logger.info("Starting enable class setting with id {}", id);
        Optional<ClassSetting> classSetting = classSettingRepository.findById(id);
        if (!classSetting.isPresent()) {
            logger.error("Can not found class setting with id {}", id);
            throw new OnlineLearningException(ErrorTypes.USER_NOT_FOUND);
        }
        classSetting.get().setStatus(false);
        classSettingRepository.save(classSetting.get());
        logger.info("Successfully enable class setting with id {}", id);
    }

    @Override
    public PaginationResponse filter(Long subjectId, Long typeId, Boolean status, String settingTitle,
                                     String classCode, Pageable pageable) {
        PagedListHolder pagedListHolder = new PagedListHolder(classSettingRepository.filter(subjectId, typeId, status,
                settingTitle, classCode));
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
    public ClassSettingResponseDto save(ClassSettingRequestDto request) {
        logger.info("Start creating content group...");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request must be not null or empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }

        ClassSetting classSetting = new ClassSetting();
        classSetting.setTypeId(request.getTypeId());
        classSetting.setClassSettingTitle(request.getClassSettingTitle());
        classSetting.setDescription(request.getDescription());
        classSetting.setDisplayOrder(request.getDisplayOrder());
        classSetting.setSettingValue(request.getSettingValue());
        classSetting.setStatus(request.getStatus());
        classSetting.setClassId(request.getClassId());
        ClassSettingResponseDto responseDto = BeanUtility.convertValue(classSettingRepository.save(classSetting),
                ClassSettingResponseDto.class);
        logger.info("Successfully add a content group");
        responseDto.setClassCode(classroomRepository.getClassroomById(request.getClassId()).getClassCode());
        return responseDto;
    }

    @Override
    public ClassSettingResponseDto update(Long id, ClassSettingRequestDto request) {
        logger.info("Start updating content group...");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request must be not null or empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        Optional<ClassSetting> classSetting = classSettingRepository.findById(id);
        if (!classSetting.isPresent()) {
            logger.error("Class setting not found with id: {}", id);
            throw new OnlineLearningException(ErrorTypes.CLASS_SETTING_NOT_FOUND, id.toString());
        }
        Optional<Classroom> classroom = classroomRepository.findById(request.getClassId());
        if (!classroom.isPresent()) {
            logger.error("Class not exist with id: {}", request.getClassId());
            throw new OnlineLearningException(ErrorTypes.CLASS_NOT_FOUND);
        }
        classSetting.get().setTypeId(request.getTypeId());
        classSetting.get().setClassSettingTitle(request.getClassSettingTitle());
        classSetting.get().setDescription(request.getDescription());
        classSetting.get().setDisplayOrder(request.getDisplayOrder());
        classSetting.get().setSettingValue(request.getSettingValue());
        classSetting.get().setStatus(request.getStatus());
        classSetting.get().setClassId(request.getClassId());
        classSettingRepository.save(classSetting.get());
        ClassSettingResponseDto responseDto = BeanUtility.convertValue(classSettingRepository.save(classSetting.get()),
                ClassSettingResponseDto.class);
        responseDto.setClassCode(classroom.get().getClassCode());
        return responseDto;
    }

    @Override
    public List<ClassSettingResponseDto> getAllByClassModule(Long subjectId) {
        List<ClassSettingResponseDto> classSettingResponse
                = classSettingRepository.filter(subjectId, ClassSetting.CLASS_MODULE, Boolean.TRUE, null, null);
        return classSettingResponse;
    }
}
