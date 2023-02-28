package swp490.g7.OnlineLearningSystem.entities.content_group.service.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.ContentGroup;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.KeywordGroup;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ContentGroupConfigTestDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ContentGroupConfigTestDtoV2;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ContentGroupDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ContentGroupExportDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ModuleContentGroupDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.request.ContentGroupRequestDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.response.ContentGroupResponseDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.repository.ContentGroupRepository;
import swp490.g7.OnlineLearningSystem.entities.content_group.service.ContentGroupService;
import swp490.g7.OnlineLearningSystem.entities.keyword.repository.KeywordRepository;
import swp490.g7.OnlineLearningSystem.entities.question.domain.Question;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.SubjectSetting;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.repository.SubjectSettingRepository;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.service.SubjectSettingService;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.BeanUtility;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ContentGroupServiceImpl implements ContentGroupService {

    private static final Logger logger = LogManager.getLogger(ContentGroupServiceImpl.class);

    @Autowired
    private ContentGroupRepository contentGroupRepository;

    @Autowired
    private SubjectSettingService subjectSettingService;

    @Autowired
    private SubjectSettingRepository subjectSettingRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Override
    public ContentGroupResponseDto findById(Long id) {
        Boolean existContentGroup = contentGroupRepository.existsById(id);
        if (!existContentGroup) {
            logger.error("Content group not exists with id: {}", id);
            throw new OnlineLearningException(ErrorTypes.CONTENT_GROUP_NOT_FOUND, id.toString());
        }
        return contentGroupRepository.getContentGroupById(id);
    }

    @Override
    public ContentGroupResponseDto save(ContentGroupRequestDto request) {
        logger.info("Start creating content group...");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request must be not null or empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        Optional<SubjectSetting> subjectSetting = subjectSettingService.findById(request.getTypeId());
        if (!subjectSetting.isPresent()) {
            logger.error("Subject Setting not exists with setting id: {}", request.getTypeId());
            throw new OnlineLearningException(ErrorTypes.SUBJECT_SETTING_NOT_FOUND);
        }

        ContentGroup contentGroup = new ContentGroup();
        contentGroup.setName(request.getName());
        contentGroup.setTypeId(request.getTypeId());
        contentGroup.setDescription(request.getDescription());
        contentGroup.setStatus(request.getStatus());
        contentGroupRepository.save(contentGroup);
        logger.info("Successfully add a content group");
        ContentGroupResponseDto responseDto = new ContentGroupResponseDto();
        responseDto.setGroupId(contentGroup.getGroupId());
        responseDto.setName(contentGroup.getName());
        responseDto.setDescription(contentGroup.getDescription());
        responseDto.setStatus(contentGroup.getStatus());
        responseDto.setTypeId(subjectSetting.get().getSubjectSettingId());
        responseDto.setSubjectSettingTitle(subjectSetting.get().getSubjectSettingTitle());
        return responseDto;
    }

    @Override
    public ContentGroupResponseDto update(Long id, ContentGroupRequestDto request) {
        logger.info("Start updating content group...");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request must be not null or empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        Optional<ContentGroup> contentGroup = contentGroupRepository.findById(id);
        if (!contentGroup.isPresent()) {
            logger.error("Content Group not found with id: {}", id);
            throw new OnlineLearningException(ErrorTypes.CONTENT_GROUP_NOT_FOUND, id.toString());
        }
        Optional<SubjectSetting> subjectSetting = subjectSettingService.findById(request.getTypeId());
        if (!subjectSetting.isPresent()) {
            logger.error("Subject Setting not exists with setting id: {}", request.getTypeId());
            throw new OnlineLearningException(ErrorTypes.SUBJECT_SETTING_NOT_FOUND);
        }
        contentGroup.get().setName(request.getName());
        contentGroup.get().setTypeId(request.getTypeId());
        contentGroup.get().setDescription(request.getDescription());
        contentGroup.get().setStatus(request.getStatus());
        contentGroupRepository.save(contentGroup.get());
        logger.info("Successfully update a content group with id: {}", id);
        ContentGroupResponseDto responseDto = new ContentGroupResponseDto();
        responseDto.setGroupId(contentGroup.get().getGroupId());
        responseDto.setName(contentGroup.get().getName());
        responseDto.setDescription(contentGroup.get().getDescription());
        responseDto.setStatus(contentGroup.get().getStatus());
        responseDto.setTypeId(subjectSetting.get().getSubjectSettingId());
        responseDto.setSubjectSettingTitle(subjectSetting.get().getSubjectSettingTitle());
        return responseDto;
    }

    @Override
    public PaginationResponse filter(Long subjectId, Long typeId, String name, Boolean status, Pageable pageable) {
        PagedListHolder pagedListHolder = new PagedListHolder(contentGroupRepository
                .filter(subjectId, name, typeId, status));
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
        logger.info("Starting enable content group with id {}", id);
        Optional<ContentGroup> contentGroup = contentGroupRepository.findById(id);
        if (!contentGroup.isPresent()) {
            logger.error("Content Group not found with id: {}", id);
            throw new OnlineLearningException(ErrorTypes.CONTENT_GROUP_NOT_FOUND, id.toString());
        }
        contentGroup.get().setStatus(true);
        contentGroupRepository.save(contentGroup.get());
        logger.info("Successfully enable content group with id {}", id);
    }

    @Override
    public void disable(Long id) {
        logger.info("Starting disable content group with id {}", id);
        Optional<ContentGroup> contentGroup = contentGroupRepository.findById(id);
        if (!contentGroup.isPresent()) {
            logger.error("Content Group not found with id: {}", id);
            throw new OnlineLearningException(ErrorTypes.CONTENT_GROUP_NOT_FOUND, id.toString());
        }
        contentGroup.get().setStatus(false);
        contentGroupRepository.save(contentGroup.get());
        logger.info("Successfully disable content group with id {}", id);
    }

    @Override
    public PaginationResponse findByTypeContent(Long id, Pageable pageable) {
        logger.info("Starting find content group with subject setting id {}", id);
        PagedListHolder pagedListHolder =
                new PagedListHolder(contentGroupRepository.findByTypeContent(id));
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
    public List<ContentGroupExportDto> getAllBySubjectIdAndTypeId(Long subjectId) {
        return contentGroupRepository.getAllBySubjectIdAndTypeId(subjectId, ContentGroup.SUBJECT_CONTENT_GROUP_TYPE);
    }

    @Override
    public void delete(Long id) {
        logger.info("Starting deleting content group with id {}", id);
        Optional<ContentGroup> contentGroup = contentGroupRepository.findById(id);
        if (!contentGroup.isPresent()) {
            logger.error("Content Group not found with id: {}", id);
            throw new OnlineLearningException(ErrorTypes.CONTENT_GROUP_NOT_FOUND, id.toString());
        }
        contentGroupRepository.delete(contentGroup.get());
        logger.info("Successfully delete content group with id {}", id);
    }

    @Override
    public List<ModuleContentGroupDto> getAllModuleWithContentGroup(Long subjectId) {
        List<ContentGroupResponseDto> contentGroups = contentGroupRepository.filter(subjectId, null, null,
                Boolean.TRUE);
        Set<Long> typeId = contentGroups.stream()
                .map(ContentGroupResponseDto::getTypeId)
                .collect(Collectors.toSet());
        List<ModuleContentGroupDto> moduleContentGroupResponse = new ArrayList<>();
        typeId.forEach(t -> {
            ModuleContentGroupDto moduleContentGroup = new ModuleContentGroupDto();
            List<ContentGroupResponseDto> filterContentGroup = contentGroups.stream()
                    .filter(c -> c.getTypeId() == t)
                    .collect(Collectors.toList());
            List<ContentGroupDto> contentGroup = BeanUtility.mapList(filterContentGroup, ContentGroupDto.class);
            moduleContentGroup.setModuleId(contentGroup.get(0).getTypeId());
            moduleContentGroup.setModuleName(
                    subjectSettingRepository.getSubjectSettingTitleBySubjectSettingId(
                            contentGroup.get(0).getTypeId()
                    )
            );
            moduleContentGroup.setContentGroups(contentGroup);
            moduleContentGroupResponse.add(moduleContentGroup);
        });
        return moduleContentGroupResponse;
    }

    @Override
    public List<ContentGroupConfigTestDto> getAllForTestConfig(Long subjectId, Long typeId) {
        List<ContentGroup> contentGroups = null;
        if (ObjectUtils.isEmpty(typeId)) {
            contentGroups = contentGroupRepository.getBySubjectId(subjectId);
        } else {
            contentGroups = contentGroupRepository.getBySubjectIdTypeId(subjectId, typeId);
        }
        if (CollectionUtils.isEmpty(contentGroups)) {
            logger.info("Content group with type id: {} is empty", typeId);
            return new ArrayList<>();
        }
        List<ContentGroupConfigTestDto> contentGroupResponse = new ArrayList<>();
        contentGroups.forEach(c -> {
            ContentGroupConfigTestDto contentGroupConfigTest = new ContentGroupConfigTestDto();
            contentGroupConfigTest.setGroupId(c.getGroupId());
            contentGroupConfigTest.setName(c.getName());
            contentGroupConfigTest.setTypeId(c.getTypeId());
            Set<Question> questions = c.getQuestions();
            contentGroupConfigTest.setNumberOfQuestion(questions.size());
            contentGroupResponse.add(contentGroupConfigTest);
        });
        return contentGroupResponse;
    }

    @Override
    public List<ContentGroupConfigTestDtoV2> getAllForTestConfigV2(Long subjectId, Long typeId) {
        List<ContentGroup> contentGroups = null;
        if (ObjectUtils.isEmpty(typeId)) {
            contentGroups = contentGroupRepository.getBySubjectId(subjectId);
        } else {
            contentGroups = contentGroupRepository.getBySubjectIdTypeId(subjectId, typeId);
        }
        if (CollectionUtils.isEmpty(contentGroups)) {
            logger.info("Content group with type id: {} is empty", typeId);
            return new ArrayList<>();
        }
        List<ContentGroupConfigTestDtoV2> contentGroupResponse = new ArrayList<>();
        contentGroups.forEach(c -> {
            ContentGroupConfigTestDtoV2 contentGroupConfigTest = new ContentGroupConfigTestDtoV2();
            contentGroupConfigTest.setGroupId(c.getGroupId());
            contentGroupConfigTest.setName(c.getName());
            contentGroupConfigTest.setTypeId(c.getTypeId());
            Set<Question> questions = c.getQuestions();
            contentGroupConfigTest.setNumberOfQuestion(questions.size());
            Map<ContentGroup, Long> quantityKeyword = c
                    .getKeywordGroups()
                    .stream()
                    .collect(
                            Collectors.groupingBy(KeywordGroup::getContentGroup, Collectors.counting())
                    );
            contentGroupConfigTest.setTotalKeywordQuantity(quantityKeyword.get(c) == null ? 0L : quantityKeyword.get(c));
            contentGroupResponse.add(contentGroupConfigTest);
        });
        return contentGroupResponse;
    }

    @Override
    public List<ModuleContentGroupDto> getAllModuleWithContentGroupForFlashCard(Long subjectId) {
        List<ContentGroupResponseDto> contentGroups = contentGroupRepository.filter(subjectId, null, null,
                Boolean.TRUE);
        Set<Long> typeId = contentGroups.stream()
                .map(ContentGroupResponseDto::getTypeId)
                .collect(Collectors.toSet());
        List<ModuleContentGroupDto> moduleContentGroupResponse = new ArrayList<>();
        typeId.forEach(t -> {
            ModuleContentGroupDto moduleContentGroup = new ModuleContentGroupDto();
            List<ContentGroupResponseDto> filterContentGroup = contentGroups.stream()
                    .filter(c -> Objects.equals(c.getTypeId(), t))
                    .collect(Collectors.toList());
            List<ContentGroupDto> contentGroup = BeanUtility.mapList(filterContentGroup, ContentGroupDto.class);
            moduleContentGroup.setModuleId(contentGroup.get(0).getTypeId());
            moduleContentGroup.setModuleName(
                    subjectSettingRepository.getSubjectSettingTitleBySubjectSettingId(
                            contentGroup.get(0).getTypeId()
                    )
            );
            moduleContentGroup.setContentGroups(contentGroup);
            moduleContentGroupResponse.add(moduleContentGroup);
        });
        moduleContentGroupResponse.forEach(md -> {
            md.getContentGroups().forEach(cg -> {
                cg.setTotalKeyword(keywordRepository.countKeywordByContentGroupId(cg.getGroupId()));
            });
        });
        return moduleContentGroupResponse;
    }
}
