package swp490.g7.OnlineLearningSystem.entities.subject_setting.service.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp490.g7.OnlineLearningSystem.entities.setting.service.impl.SettingServiceImpl;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;
import swp490.g7.OnlineLearningSystem.entities.subject.repository.SubjectRepository;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.SubjectSetting;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.request.SubjectSettingRequestDto;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.response.SubjectSettingResponseDto;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.repository.SubjectSettingRepository;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.service.SubjectSettingService;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.BeanUtility;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectSettingServiceImpl implements SubjectSettingService {

    private static final Logger logger = LogManager.getLogger(SettingServiceImpl.class);

    @Autowired
    private SubjectSettingRepository subjectSettingRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public Optional<SubjectSetting> findById(Long id) {
        Optional<SubjectSetting> subjectSetting = subjectSettingRepository.findById(id);
        if (!subjectSetting.isPresent()) {
            logger.error("Subject Setting not exists with setting id: {}", id);
            throw new OnlineLearningException(ErrorTypes.SUBJECT_SETTING_NOT_FOUND);
        }
        return subjectSettingRepository.findById(id);
    }

    @Override
    public SubjectSettingResponseDto save(SubjectSettingRequestDto request) {
        logger.info("Start creating content group...");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request must be not null or empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        Optional<Subject> subject = subjectRepository.findById(request.getSubjectId());
        if (!subject.isPresent()) {
            logger.error("Subject Setting not exists with setting id: {}", request.getSubjectId());
            throw new OnlineLearningException(ErrorTypes.SUBJECT_SETTING_NOT_FOUND);
        }

        SubjectSetting subjectSetting = new SubjectSetting();
        subjectSetting.setTypeId(request.getTypeId());
        subjectSetting.setSubjectSettingTitle(request.getSubjectSettingTitle());
        subjectSetting.setDescription(request.getDescription());
        subjectSetting.setSettingValue(request.getSettingValue());
        subjectSetting.setDisplayOrder(request.getDisplayOrder());
        subjectSetting.setStatus(request.getStatus());
        subjectSetting.setSubjectId(subject.get().getSubjectId());
        SubjectSettingResponseDto responseDto = BeanUtility.convertValue
                (subjectSettingRepository.save(subjectSetting), SubjectSettingResponseDto.class);
        logger.info("Successfully add a content group");
        responseDto.setSubjectCode(subject.get().getSubjectCode());
        return responseDto;
    }

    @Override
    public SubjectSettingResponseDto update(Long id, SubjectSettingRequestDto request) {
        logger.info("Start updating content group...");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request must be not null or empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        Optional<SubjectSetting> subjectSetting = subjectSettingRepository.findById(id);
        if (!subjectSetting.isPresent()) {
            logger.error("subject setting not found with id: {}", id);
            throw new OnlineLearningException(ErrorTypes.SUBJECT_SETTING_NOT_FOUND, id.toString());
        }
        Optional<Subject> subject = subjectRepository.findById(request.getSubjectId());
        if (!subject.isPresent()) {
            logger.error("subject not exist with id: {}", request.getSubjectId());
            throw new OnlineLearningException(ErrorTypes.SUBJECT_WITH_ID_NOT_FOUND);
        }
        subjectSetting.get().setTypeId(request.getTypeId());
        subjectSetting.get().setSubjectSettingTitle(request.getSubjectSettingTitle());
        subjectSetting.get().setDescription(request.getDescription());
        subjectSetting.get().setDisplayOrder(request.getDisplayOrder());
        subjectSetting.get().setSettingValue(request.getSettingValue());
        subjectSetting.get().setStatus(request.getStatus());
        subjectSetting.get().setSubjectId(request.getSubjectId());
        subjectSettingRepository.save(subjectSetting.get());
        SubjectSettingResponseDto responseDto = BeanUtility.convertValue(
                subjectSettingRepository.save(subjectSetting.get()), SubjectSettingResponseDto.class);
        responseDto.setSubjectCode(subject.get().getSubjectCode());
        return responseDto;
    }

    @Override
    public void enable(Long id) {
        logger.info("Starting enable subject setting with id {}", id);
        Optional<SubjectSetting> subjectSetting = subjectSettingRepository.findById(id);
        if (!subjectSetting.isPresent()) {
            logger.error("Can not found user with id {}", id);
            throw new OnlineLearningException(ErrorTypes.SUBJECT_SETTING_NOT_FOUND);
        }
        subjectSetting.get().setStatus(true);
        subjectSettingRepository.save(subjectSetting.get());
        logger.info("Successfully enable subject setting with id {}", id);
    }

    @Override
    public void disable(Long id) {
        logger.info("Starting enable subject setting with id {}", id);
        Optional<SubjectSetting> subjectSetting = subjectSettingRepository.findById(id);
        if (!subjectSetting.isPresent()) {
            logger.error("Can not found subject setting with id {}", id);
            throw new OnlineLearningException(ErrorTypes.SUBJECT_SETTING_NOT_FOUND);
        }
        subjectSetting.get().setStatus(false);
        subjectSettingRepository.save(subjectSetting.get());
        logger.info("Successfully enable subject setting with id {}", id);
    }

    @Override
    public PaginationResponse filter(Long subjectId, Long typeId, Boolean status, String settingTitle, String subjectCode,
                                     Pageable pageable) {
        PagedListHolder pagedListHolder = new PagedListHolder(subjectSettingRepository.filter(subjectId, typeId, status,
                settingTitle, subjectCode));
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
    public PaginationResponse getSubjectModule(Long subjectId, Pageable pageable) {
        PagedListHolder pagedListHolder = new PagedListHolder(subjectSettingRepository.filter(subjectId,
                SubjectSetting.SUBJECT_MODULE, Boolean.TRUE, null, null));
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
    public List<SubjectSettingResponseDto> getAllBySubjectModuleOrSubjectContent(Long subjectId, Long typeId) {
        List<SubjectSettingResponseDto> subjectSettings
                = subjectSettingRepository.getAllBySubjectModuleOrSubjectContent(subjectId, typeId);
        return subjectSettings;
    }
}
