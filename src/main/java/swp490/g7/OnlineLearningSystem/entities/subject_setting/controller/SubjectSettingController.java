package swp490.g7.OnlineLearningSystem.entities.subject_setting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swp490.g7.OnlineLearningSystem.annotations.UserPermission;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.SubjectSetting;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.request.SubjectSettingRequestDto;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.response.SubjectSettingResponseDto;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.service.SubjectSettingService;
import swp490.g7.OnlineLearningSystem.utilities.Constants;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/subject-setting")
public class SubjectSettingController {

    @Autowired
    SubjectSettingService subjectSettingService;

    @GetMapping("/{id}")
    public SubjectSetting findById(@PathVariable("id") Long id) {
        return subjectSettingService.findById(id).get();
    }

    @UserPermission(name = Constants.SUBJECT_SETTING_DETAILS, type = Constants.ADD)
    @PostMapping("")
    public SubjectSettingResponseDto create(@Valid @RequestBody SubjectSettingRequestDto request) {
        return subjectSettingService.save(request);
    }

    @UserPermission(name = Constants.SUBJECT_SETTING_DETAILS, type = Constants.UPDATE)
    @PutMapping("/{id}")
    public SubjectSettingResponseDto update(@PathVariable("id") Long id, @Valid @RequestBody SubjectSettingRequestDto request) {
        return subjectSettingService.update(id, request);
    }

    @UserPermission(name = Constants.SUBJECT_SETTING_DETAILS, type = Constants.UPDATE)
    @PutMapping("/{id}/enable")
    public void enable(@PathVariable("id") Long id) {
        subjectSettingService.enable(id);
    }

    @UserPermission(name = Constants.SUBJECT_SETTING_DETAILS, type = Constants.UPDATE)
    @PutMapping("/{id}/disable")
    public void disable(@PathVariable("id") Long id) {
        subjectSettingService.disable(id);
    }

    @GetMapping("/filter")
    public PaginationResponse filter(@RequestParam("subjectId") Long subjectId,
                                     @RequestParam(value = "typeId", required = false) Long typeId,
                                     @RequestParam(value = "status", required = false) Boolean status,
                                     @RequestParam(value = "settingTitle", required = false) String settingTitle,
                                     @RequestParam(value = "subjectCode", required = false) String subjectCode,
                                     Pageable pageable) {
        return subjectSettingService.filter(subjectId, typeId, status, settingTitle, subjectCode, pageable);
    }

    @GetMapping("/content-group")
    public List<SubjectSettingResponseDto> getAllBySubjectContent(@RequestParam("subjectId") Long subjectId) {
        return subjectSettingService.getAllBySubjectModuleOrSubjectContent(subjectId, SubjectSetting.SUBJECT_CONTENT_GROUP_TYPES);
    }

    @GetMapping("/subject-module")
    public List<SubjectSettingResponseDto> getAllBySubjectModule(@RequestParam("subjectId") Long subjectId) {
        return subjectSettingService.getAllBySubjectModuleOrSubjectContent(subjectId, SubjectSetting.SUBJECT_MODULE);
    }
}
