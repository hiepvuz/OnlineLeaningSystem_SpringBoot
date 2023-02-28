package swp490.g7.OnlineLearningSystem.entities.content_group.service;

import org.springframework.data.domain.Pageable;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ContentGroupConfigTestDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ContentGroupConfigTestDtoV2;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ContentGroupExportDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ModuleContentGroupDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.request.ContentGroupRequestDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.response.ContentGroupResponseDto;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.List;

public interface ContentGroupService {
    ContentGroupResponseDto findById(Long id);

    ContentGroupResponseDto save(ContentGroupRequestDto request);

    ContentGroupResponseDto update(Long id, ContentGroupRequestDto request);

    PaginationResponse filter(Long subjectId, Long typeId, String name, Boolean status, Pageable pageable);

    void enable(Long id);

    void disable(Long id);

    PaginationResponse findByTypeContent(Long id, Pageable pageable);

    List<ContentGroupExportDto> getAllBySubjectIdAndTypeId(Long subjectId);

    void delete(Long id);

    List<ModuleContentGroupDto> getAllModuleWithContentGroup(Long subjectId);

    List<ContentGroupConfigTestDto> getAllForTestConfig(Long subjectId, Long typeId);

    List<ContentGroupConfigTestDtoV2> getAllForTestConfigV2(Long subjectId, Long typeId);

    List<ModuleContentGroupDto> getAllModuleWithContentGroupForFlashCard(Long subjectId);
}
