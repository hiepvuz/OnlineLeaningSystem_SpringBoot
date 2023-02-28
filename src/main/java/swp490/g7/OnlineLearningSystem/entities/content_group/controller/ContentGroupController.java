package swp490.g7.OnlineLearningSystem.entities.content_group.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ContentGroupConfigTestDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ContentGroupConfigTestDtoV2;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ContentGroupExportDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ModuleContentGroupDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.request.ContentGroupRequestDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.response.ContentGroupResponseDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.service.ContentGroupService;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/content-group")
public class ContentGroupController {

    @Autowired
    private ContentGroupService contentGroupService;

    @GetMapping("/{id}/type-id")
    public PaginationResponse findByTypeContent(@PathVariable Long id, Pageable pageable) {
        return contentGroupService.findByTypeContent(id, pageable);
    }

    @GetMapping("/{id}")
    public ContentGroupResponseDto findById(@PathVariable Long id) {
        return contentGroupService.findById(id);
    }

    @PostMapping("")
    public ContentGroupResponseDto create(@Valid @RequestBody ContentGroupRequestDto request) {
        return contentGroupService.save(request);
    }

    @PutMapping("/{id}")
    public ContentGroupResponseDto update(@PathVariable Long id, @Valid @RequestBody ContentGroupRequestDto request) {
        return contentGroupService.update(id, request);
    }

    @GetMapping("/filter")
    public PaginationResponse filter(@RequestParam(value = "subjectId") Long subjectId,
                                     @RequestParam(value = "typeId", required = false) Long typeId,
                                     @RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "status", required = false) Boolean status,
                                     Pageable pageable) {
        return contentGroupService.filter(subjectId, typeId, name, status, pageable);
    }

    @PutMapping("/{id}/enable")
    public void enable(@PathVariable("id") Long id) {
        contentGroupService.enable(id);
    }

    @PutMapping("/{id}/disable")
    public void disable(@PathVariable("id") Long id) {
        contentGroupService.disable(id);
    }

    @GetMapping("/export-content-group")
    List<ContentGroupExportDto> getContentGroupForExportBySubjectId(@RequestParam("subjectId") Long subjectId) {
        return contentGroupService.getAllBySubjectIdAndTypeId(subjectId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        contentGroupService.delete(id);
    }

    @GetMapping("/module-content-group")
    public List<ModuleContentGroupDto> getAllModuleWithContentGroup(@RequestParam(value = "subjectId") Long subjectId) {
        return contentGroupService.getAllModuleWithContentGroup(subjectId);
    }

    @GetMapping("/all")
    public List<ContentGroupConfigTestDto> getAllForTestConfig(@RequestParam("subjectId") Long subjectId,
                                                               @RequestParam(value = "typeId", required = false) Long typeId) {
        return contentGroupService.getAllForTestConfig(subjectId, typeId);
    }

    @GetMapping("/all-v2")
    public List<ContentGroupConfigTestDtoV2> getAllForTestConfigV2(@RequestParam("subjectId") Long subjectId,
                                                                   @RequestParam(value = "typeId", required = false) Long typeId) {
        return contentGroupService.getAllForTestConfigV2(subjectId, typeId);
    }

    @GetMapping("/module-content-group/flash-card")
    public List<ModuleContentGroupDto> getAllModuleWithContentGroupForFlashCard(@RequestParam(value = "subjectId") Long subjectId) {
        return contentGroupService.getAllModuleWithContentGroupForFlashCard(subjectId);
    }
}
