package swp490.g7.OnlineLearningSystem.entities.keyword.service.impl;

import com.poiji.bind.Poiji;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.ContentGroup;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.KeywordGroup;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.KeywordGroupId;
import swp490.g7.OnlineLearningSystem.entities.content_group.repository.ContentGroupRepository;
import swp490.g7.OnlineLearningSystem.entities.keyword.domain.Keyword;
import swp490.g7.OnlineLearningSystem.entities.keyword.domain.dto.KeywordExcelDto;
import swp490.g7.OnlineLearningSystem.entities.keyword.domain.request.KeywordRequestDto;
import swp490.g7.OnlineLearningSystem.entities.keyword.domain.response.KeywordResponseDto;
import swp490.g7.OnlineLearningSystem.entities.keyword.repository.KeywordGroupRepository;
import swp490.g7.OnlineLearningSystem.entities.keyword.repository.KeywordRepository;
import swp490.g7.OnlineLearningSystem.entities.keyword.service.KeywordService;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.SubjectSetting;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.repository.SubjectSettingRepository;
import swp490.g7.OnlineLearningSystem.errorhandling.CommonErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.ErrorTypes;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.BeanUtility;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KeywordServiceImpl implements KeywordService {

    private static final Logger logger = LogManager.getLogger(KeywordServiceImpl.class);

    @Autowired
    private KeywordRepository keywordRepository;

    @Autowired
    private ContentGroupRepository contentGroupRepository;

    @Autowired
    private SubjectSettingRepository subjectSettingRepository;

    @Autowired
    private KeywordGroupRepository keywordGroupRepository;

    @Override
    public PaginationResponse findByGroupId(Long contentGroupId, Long subjectId, Pageable pageable) {
        PagedListHolder pagedListHolder =
                new PagedListHolder(keywordRepository.findByGroupId(contentGroupId, subjectId));
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
    public KeywordResponseDto findById(Long id) {
        Boolean existKeyword = keywordRepository.existsById(id);
        if (!existKeyword) {
            logger.error("Keyword not exists with id: {}", id);
            throw new OnlineLearningException(ErrorTypes.KEYWORD_NOT_FOUND, id.toString());
        }
        return keywordRepository.getKeywordById(id);
    }

    @Override
    public KeywordResponseDto save(KeywordRequestDto request) {
        logger.info("Start creating keyword...");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request must be not null or empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        Optional<SubjectSetting> subjectSetting = subjectSettingRepository.findById(request.getCategoryId());
        if (!subjectSetting.isPresent()) {
            logger.error("Subject Setting not exists with setting id: {}", request.getCategoryId());
            throw new OnlineLearningException(ErrorTypes.SUBJECT_SETTING_NOT_FOUND);
        }
        if (keywordRepository.existsByKeyword(request.getKeyword())) {
            logger.info("Keyword must be unique: {}!", request.getKeyword());
            throw new OnlineLearningException(ErrorTypes.KEYWORD_MUST_BE_UNIQUE, request.getKeyword());
        }
        Keyword keyword = new Keyword();
        keyword.setKeyword(request.getKeyword());
        keyword.setExcerpt(request.getExcerpt());
        keyword.setBody(request.getBody());
        keyword.setStatus(request.getStatus());
        keyword.setCategoryId(request.getCategoryId());
        if (CollectionUtils.isNotEmpty(request.getContentGroupIds())) {
            List<ContentGroup> contentGroups = contentGroupRepository.findByGroupIdIn(request.getContentGroupIds());
            List<KeywordGroup> keywordGroups = new ArrayList<>();
            contentGroups.forEach(c -> {
                KeywordGroup keywordGroup = new KeywordGroup();
                KeywordGroupId keywordGroupId = new KeywordGroupId(keyword.getKeywordId(), c.getGroupId());
                keywordGroup.setId(keywordGroupId);
                keywordGroup.setKeyword(keyword);
                Optional<ContentGroup> contentGroup = contentGroupRepository.findById(c.getGroupId());
                if (!contentGroup.isPresent()) {
                    logger.error("Content group not found with id: {}", c.getGroupId());
                    throw new OnlineLearningException(ErrorTypes.CONTENT_GROUP_NOT_FOUND, c.getGroupId().toString());
                }
                keywordGroup.setContentGroup(contentGroup.get());
                keywordGroups.add(keywordGroup);
            });
            keyword.setKeywordGroups(keywordGroups);
        }
        keywordRepository.save(keyword);
        logger.info("Successfully create a keyword");
        KeywordResponseDto responseDto = BeanUtility.convertValue(keyword, KeywordResponseDto.class);
        responseDto.setCategoryId(subjectSetting.get().getSubjectSettingId());
        responseDto.setSubjectSettingTitle(subjectSetting.get().getSubjectSettingTitle());
        return responseDto;
    }

    @Override
    public KeywordResponseDto update(Long id, KeywordRequestDto request) {
        logger.info("Start creating keyword...");
        if (ObjectUtils.isEmpty(request)) {
            logger.error("Request must be not null or empty");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        }
        Optional<Keyword> keyword = keywordRepository.findById(id);
        if (!keyword.isPresent()) {
            logger.error("Keyword not exists with id: {}", id);
            throw new OnlineLearningException(ErrorTypes.KEYWORD_NOT_FOUND, id.toString());
        }
        Optional<SubjectSetting> subjectSetting = subjectSettingRepository.findById(request.getCategoryId());
        if (!subjectSetting.isPresent()) {
            logger.error("Subject Setting not exists with setting id: {}", request.getCategoryId());
            throw new OnlineLearningException(ErrorTypes.SUBJECT_SETTING_NOT_FOUND);
        }
        if (!request.getKeyword().equals(keyword.get().getKeyword())) {
            if (keywordRepository.existsByKeyword(request.getKeyword())) {
                logger.info("Keyword must be unique: {}!", request.getKeyword());
                throw new OnlineLearningException(ErrorTypes.KEYWORD_MUST_BE_UNIQUE, request.getKeyword());
            }
        }
        keyword.get().setKeyword(request.getKeyword());
        keyword.get().setExcerpt(request.getExcerpt());
        keyword.get().setBody(request.getBody());
        keyword.get().setStatus(request.getStatus());
        keyword.get().setCategoryId(request.getCategoryId());
        if (CollectionUtils.isNotEmpty(request.getContentGroupIds())) {
            List<ContentGroup> contentGroups = contentGroupRepository.findByGroupIdIn(request.getContentGroupIds());
            List<KeywordGroup> keywordGroups = new ArrayList<>();
            contentGroups.forEach(c -> {
                KeywordGroup keywordGroup = new KeywordGroup();
                KeywordGroupId keywordGroupId = new KeywordGroupId(keyword.get().getKeywordId(), c.getGroupId());
                keywordGroup.setId(keywordGroupId);
                keywordGroup.setKeyword(keyword.get());
                Optional<ContentGroup> contentGroup = contentGroupRepository.findById(c.getGroupId());
                if (!contentGroup.isPresent()) {
                    logger.error("Content group not found with id: {}", c.getGroupId());
                    throw new OnlineLearningException(ErrorTypes.CONTENT_GROUP_NOT_FOUND, c.getGroupId().toString());
                }
                keywordGroup.setContentGroup(contentGroup.get());
                keywordGroups.add(keywordGroup);
            });
            keyword.get().setKeywordGroups(keywordGroups);
        }
        keywordRepository.save(keyword.get());

        logger.info("Successfully update a keyword");

        KeywordResponseDto responseDto = new KeywordResponseDto();
        responseDto.setKeywordId(keyword.get().getKeywordId());
        responseDto.setKeyword(keyword.get().getKeyword());
        responseDto.setExcerpt(keyword.get().getExcerpt());
        responseDto.setBody(keyword.get().getBody());
        responseDto.setStatus(keyword.get().getStatus());
        responseDto.setCategoryId(subjectSetting.get().getSubjectSettingId());
        responseDto.setSubjectSettingTitle(subjectSetting.get().getSubjectSettingTitle());
        return responseDto;
    }

    @Override
    public PaginationResponse filter(String keyword, Boolean status, Long categoryId, Long groupId, Long subjectId, Pageable pageable) {
        PagedListHolder pagedListHolder =
                new PagedListHolder(keywordRepository.filter(keyword, status, categoryId, groupId, subjectId));
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
        logger.info("Starting enable keyword with id {}", id);
        Optional<Keyword> keyword = keywordRepository.findById(id);
        if (!keyword.isPresent()) {
            logger.error("Keyword not found with id: {}", id);
            throw new OnlineLearningException(ErrorTypes.KEYWORD_NOT_FOUND, id.toString());
        }
        keyword.get().setStatus(true);
        keywordRepository.save(keyword.get());
        logger.info("Successfully enable keyword with id {}", id);
    }

    @Override
    public void disable(Long id) {
        logger.info("Starting disable keyword with id {}", id);
        Optional<Keyword> keyword = keywordRepository.findById(id);
        if (!keyword.isPresent()) {
            logger.error("Keyword not found with id: {}", id);
            throw new OnlineLearningException(ErrorTypes.KEYWORD_NOT_FOUND, id.toString());
        }
        keyword.get().setStatus(false);
        keywordRepository.save(keyword.get());
        logger.info("Successfully disable content group with id {}", id);
    }

    @Override
    public void importKeyWord(Long categoryId, MultipartFile file) {
        File fileConverted = convertMultiPartToFile(file);
        List<KeywordExcelDto> keywordImport = Poiji.fromExcel(fileConverted, KeywordExcelDto.class);
        createKeywordFromExcel(categoryId, keywordImport);
    }

    @Transactional(rollbackFor = {Exception.class})
    public void createKeywordFromExcel(Long categoryId, List<KeywordExcelDto> keywordImport) {
        keywordImport.forEach(k -> {
            Keyword keyword = new Keyword();
            keyword.setKeyword(k.getKeyword());
            keyword.setStatus(Boolean.TRUE);
            keyword.setExcerpt(k.getExcerpt());
            keyword.setCategoryId(categoryId);
            keyword.setBody(k.getBody());
            List<Long> groupIds = Arrays.asList(k.getGroupId().split(",")).stream()
                    .map(s -> Long.parseLong(s.trim()))
                    .collect(Collectors.toList());
            List<ContentGroup> contentGroups = contentGroupRepository.findByGroupIdIn(groupIds);

            if (CollectionUtils.isEmpty(contentGroups)) {
                logger.error("Content group not found with id: {}", k.getGroupId());
                throw new OnlineLearningException(ErrorTypes.CONTENT_GROUP_NOT_FOUND, k.getGroupId());
            }
            List<KeywordGroup> keywordGroups = new ArrayList<>();
            contentGroups.forEach(c -> {
                KeywordGroup keywordGroup = new KeywordGroup();
                KeywordGroupId keywordGroupId = new KeywordGroupId(keyword.getKeywordId(), c.getGroupId());
                keywordGroup.setId(keywordGroupId);
                keywordGroup.setKeyword(keyword);
                Optional<ContentGroup> contentGroup = contentGroupRepository.findById(c.getGroupId());
                if (!contentGroup.isPresent()) {
                    logger.error("Content group not found with id: {}", c.getGroupId());
                    throw new OnlineLearningException(ErrorTypes.CONTENT_GROUP_NOT_FOUND, c.getGroupId().toString());
                }
                keywordGroup.setContentGroup(contentGroup.get());
                keywordGroups.add(keywordGroup);
            });

            keyword.setKeywordGroups(keywordGroups);
            keywordRepository.save(keyword);
        });
    }

    private File convertMultiPartToFile(MultipartFile file) {
        File convertFile = new File(file.getOriginalFilename());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(convertFile);
            fos.write(file.getBytes());
        } catch (IOException e) {
            logger.error("Convert file failed!");
            throw new OnlineLearningException(ErrorTypes.INVALID_REQUEST);
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                logger.error("Close File Output Stream failed!");
                throw new OnlineLearningException(CommonErrorTypes.INTERNAL_SERVER_ERROR);
            }
        }
        return convertFile;
    }
}
