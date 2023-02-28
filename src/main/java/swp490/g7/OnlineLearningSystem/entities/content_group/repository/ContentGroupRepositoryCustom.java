package swp490.g7.OnlineLearningSystem.entities.content_group.repository;

import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ContentGroupExportDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.response.ContentGroupResponseDto;

import java.util.List;

public interface ContentGroupRepositoryCustom {

    ContentGroupResponseDto getContentGroupById(Long id);

    List<ContentGroupResponseDto> filter(Long subjectId, String name, Long typeId, Boolean status);

    List<ContentGroupResponseDto> findByTypeContent(Long id);

    List<ContentGroupExportDto> getAllBySubjectIdAndTypeId(Long subjectId, Long typeId);
}
