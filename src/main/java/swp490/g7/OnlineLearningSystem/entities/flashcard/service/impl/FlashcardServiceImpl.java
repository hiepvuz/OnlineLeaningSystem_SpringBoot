package swp490.g7.OnlineLearningSystem.entities.flashcard.service.impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.ContentGroup;
import swp490.g7.OnlineLearningSystem.entities.content_group.repository.ContentGroupRepository;
import swp490.g7.OnlineLearningSystem.entities.flashcard.domain.dto.FlashcardDto;
import swp490.g7.OnlineLearningSystem.entities.flashcard.domain.entity.Flashcard;
import swp490.g7.OnlineLearningSystem.entities.flashcard.domain.entity.FlashcardConfig;
import swp490.g7.OnlineLearningSystem.entities.flashcard.domain.req.KeywordEntities;
import swp490.g7.OnlineLearningSystem.entities.flashcard.domain.res.FlashcardListResponse;
import swp490.g7.OnlineLearningSystem.entities.flashcard.repository.FlashcardConfigRepository;
import swp490.g7.OnlineLearningSystem.entities.flashcard.repository.FlashcardRepository;
import swp490.g7.OnlineLearningSystem.entities.flashcard.service.FlashcardService;
import swp490.g7.OnlineLearningSystem.entities.keyword.domain.Keyword;
import swp490.g7.OnlineLearningSystem.entities.keyword.domain.dto.KeywordDto;
import swp490.g7.OnlineLearningSystem.entities.keyword.repository.KeywordRepository;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.SubjectSetting;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.repository.SubjectSettingRepository;
import swp490.g7.OnlineLearningSystem.errorhandling.OnlineLearningException;
import swp490.g7.OnlineLearningSystem.utilities.GsonUtils;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlashcardServiceImpl implements FlashcardService {
    private final FlashcardRepository flashcardRepository;
    private final SubjectSettingRepository subjectSettingRepository;
    private final ContentGroupRepository contentGroupRepository;
    private final FlashcardConfigRepository flashcardConfigRepository;
    private final KeywordRepository keywordRepository;

    @Override
    @Transactional
    public FlashcardDto createOrUpdateFlashCard(FlashcardDto flashcardDto) {
        Flashcard flashcard = new Flashcard();
        if (flashcardDto.getId() != null) {
            flashcard = flashcardRepository.getById(flashcardDto.getId());
        }
        flashcard.setTitle(flashcardDto.getTitle());
        flashcard.setReviewPurpose(flashcardDto.getReviewPurpose());

        if (flashcardDto.getContentId() != null) {
            Optional<SubjectSetting> subjectSetting = subjectSettingRepository.findBySubjectSettingId(flashcardDto.getContentId());
            if (subjectSetting.isPresent()) {
                flashcard.setSubjectSetting(subjectSetting.get());
                Map<Long, Integer> mapFlashCardConfig = flashcardDto.getKeywordEntities()
                        .stream()
                        .filter(item -> item.getContentId() != null)
                        .collect(Collectors.toMap(KeywordEntities::getContentId, KeywordEntities::getQuantity));
                List<ContentGroup> contentGroupList = contentGroupRepository.findByGroupIdIn(mapFlashCardConfig.keySet());

                final Flashcard finalFlashcard = flashcard;
                List<FlashcardConfig> flashcardConfigList = contentGroupList.stream().map(item -> {
                    FlashcardConfig fl = new FlashcardConfig();
                    fl.setFlashcard(finalFlashcard);
                    fl.setContentGroup(item);
                    fl.setQuantity(mapFlashCardConfig.get(item.getGroupId()));
                    return fl;
                }).collect(Collectors.toList());
                flashcardConfigRepository.saveAll(flashcardConfigList);
            }
        }
        flashcardRepository.save(flashcard);
        flashcardDto.setId(flashcard.getId());
        return flashcardDto;
    }

    @Override
    public FlashcardDto getFlashcardById(Long id) {
        Optional<Flashcard> flashcardOptional = flashcardRepository.findById(id);
        if (flashcardOptional.isPresent()) {
            Flashcard flashcard = flashcardOptional.get();
            FlashcardDto flashcardDetailResponse = new FlashcardDto();
            flashcardDetailResponse.setId(flashcard.getId());
            flashcardDetailResponse.setTitle(flashcard.getTitle());
            flashcardDetailResponse.setReviewPurpose(flashcard.getReviewPurpose());
            List<FlashcardConfig> flashcardConfigList = flashcard.getFlashcardConfigs();
            List<KeywordEntities> keywordEntities = flashcardConfigList.stream().map(item -> {
                KeywordEntities keyword = new KeywordEntities();
                if (item.getContentGroup() != null) {
                    keyword.setContentId(item.getContentGroup().getGroupId());
                }
                keyword.setQuantity(item.getQuantity());
                return keyword;
            }).collect(Collectors.toList());
            flashcardDetailResponse.setKeywordEntities(keywordEntities);
            return flashcardDetailResponse;
        }
        return null;
    }

    @Override
    public PaginationResponse<Object> getListFlashCardByContentGroupId(Long contentGroupId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        //if contentGroupId is null, fetch all flashcard in database
        if (contentGroupId == null) {
            Page<Flashcard> flashcardList = flashcardRepository.getPageFlashcardByIsDelete(pageable);
            List<FlashcardListResponse> flashcardListResponses = flashcardList.stream().map(item -> {
                List<FlashcardConfig> flashcardConfigList = item.getFlashcardConfigs();
                Set<Long> contentId = new LinkedHashSet<>();
                List<KeywordEntities> keywordEntities = flashcardConfigList
                        .stream()
                        .map(flashcardConfig -> {
                            KeywordEntities keyword = new KeywordEntities();
                            if (flashcardConfig.getContentGroup() != null) {
                                contentId.add(contentGroupRepository.getTypeIdByContentGroupId(
                                        flashcardConfig.getContentGroup().getGroupId())
                                );
                                keyword.setContentId(flashcardConfig.getContentGroup().getGroupId());
                            }
                            keyword.setQuantity(flashcardConfig.getQuantity());
                            return keyword;
                        }).collect(Collectors.toList());
                FlashcardListResponse response = new FlashcardListResponse();
                response.setId(item.getId());
                response.setContentId(contentId
                        .stream()
                        .filter(Objects::nonNull)
                        .findFirst()
                        .orElse(null));
                response.setTitle(item.getTitle());
                response.setReviewPurpose(item.getReviewPurpose());
                response.setKeywordEntities(keywordEntities);
                return response;
            }).collect(Collectors.toList());

            return PaginationResponse.builder()
                    .total(flashcardList.getTotalElements())
                    .numberOfPage(size)
                    .pageIndex(page)
                    .items(Collections.singletonList(flashcardListResponses))
                    .build();

        }

        ContentGroup contentGroup = contentGroupRepository.getById(contentGroupId);

        List<FlashcardConfig> flashcardConfigList = contentGroup.getFlashcardConfigs();
        Set<Long> flashcardIdsList = flashcardConfigList.stream().map(item -> {
            if (item.getFlashcard() != null) {
                return item.getFlashcard().getId();
            }
            return null;
        }).collect(Collectors.toSet());
        Page<Flashcard> flashcardPage = flashcardRepository.findByIdIn(flashcardIdsList, pageable);
        List<FlashcardListResponse> flashcardListResponses = flashcardPage.stream().map(item -> {
            List<FlashcardConfig> flashcardConfigs = item.getFlashcardConfigs();
            Set<Long> contentId = new LinkedHashSet<>();
            List<KeywordEntities> keywordEntitiesList = flashcardConfigs
                    .stream()
                    .map(flashcardConfig -> {
                        KeywordEntities keyword = new KeywordEntities();
                        if (flashcardConfig.getContentGroup() != null) {
                            contentId.add(contentGroupRepository.getTypeIdByContentGroupId(
                                    flashcardConfig.getContentGroup().getGroupId())
                            );
                            keyword.setContentId(flashcardConfig.getContentGroup().getGroupId());
                        }
                        keyword.setQuantity(flashcardConfig.getQuantity());
                        return keyword;
                    }).collect(Collectors.toList());
            FlashcardListResponse response = new FlashcardListResponse();
            response.setTitle(item.getTitle());
            response.setId(item.getId());
            response.setReviewPurpose(item.getReviewPurpose());
            response.setKeywordEntities(keywordEntitiesList);
            response.setContentId(contentId
                    .stream()
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null));
            return response;
        }).collect(Collectors.toList());

        return PaginationResponse.builder()
                .total(flashcardPage.getTotalElements())
                .numberOfPage(size)
                .pageIndex(page)
                .items(Collections.singletonList(flashcardListResponses))
                .build();
    }

    @Override
    @Transactional
    public void deleteFlashcardById(Long id) {
        // soft delete
        Optional<Flashcard> flashcardOpt = flashcardRepository.findById(id);
        if (!flashcardOpt.isPresent()) {
            throw new OnlineLearningException("Cannot find flashcard with id: " + id);
        }
        Flashcard flashcard = flashcardOpt.get();
        flashcard.setIsDelete(true);
        flashcardRepository.save(flashcard);
    }

    @Override
    @SneakyThrows
    public List<List<Keyword>> findKeywordEntitiesByFlashcardId(Long flashcardId) {
        Gson gson = new Gson();
        List<Keyword> res = new ArrayList<>();
        // Get list flashcard config by flashcard id
        List<FlashcardConfig> flashcardConfigOpt = flashcardConfigRepository.findFlascardConfigByFlashcardId(flashcardId);

        // If flashcard config wasn't exist => throw exception
        if (flashcardConfigOpt.isEmpty()) {
            throw new OnlineLearningException(String.format("cannot find config with flashcardId: %s", flashcardId));
        }

        //Iterator list flashcard config
        List<List<Keyword>> fc = new ArrayList<>();

        flashcardConfigOpt.forEach(flashcardConfig -> {
            Integer quantity = flashcardConfig.getQuantity();

            //if quantity null set default = 10
            if (quantity == null) {
                quantity = 10;
            }

            /* get list keyword for this knowledge review */
            /*
             * check if column keyword list in flashcard config null or empty
             * -> create new one following the quantity in flashcard config table
             */
            if (flashcardConfig.getKeywordList() == null ||
                    flashcardConfig.getKeywordList().isEmpty() ||
                    flashcardConfig.getKeywordList().equals("[]")) {

                //get list keyword by contentGroupId and quantity in flashcard config table
                List<Keyword> keywordEntities = keywordRepository
                        .findKeywordRandomByGroupIdAndQuantity(
                                flashcardConfig.getContentGroup().getGroupId(), quantity);

                // convert list keyword to keyword dto
                List<KeywordDto> keywordDtos = keywordEntities
                        .stream()
                        .map(KeywordDto::new)
                        .collect(Collectors.toList());

                // convert list object to json object and set it to flashcard config
                String keywordEntity = gson.toJson(keywordDtos);
                flashcardConfig.setKeywordList(keywordEntity);

                //save into db
                flashcardConfigRepository.save(flashcardConfig);

                // add to response
                res.addAll(keywordEntities);
            } else {
                //if keyword list existed in db, get it and add to response
                List<KeywordDto> keywordListInFlashcardConfig = GsonUtils.stringToArray(flashcardConfig.getKeywordList(), KeywordDto[].class);
                res.addAll(keywordListInFlashcardConfig.stream().map(Keyword::new).collect(Collectors.toList()));
            }
        });

        fc.add(res);

        return fc;
    }
}
