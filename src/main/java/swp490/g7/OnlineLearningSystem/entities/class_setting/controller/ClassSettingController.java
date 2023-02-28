package swp490.g7.OnlineLearningSystem.entities.class_setting.controller;

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
import swp490.g7.OnlineLearningSystem.entities.class_setting.domain.request.ClassSettingRequestDto;
import swp490.g7.OnlineLearningSystem.entities.class_setting.domain.response.ClassSettingResponseDto;
import swp490.g7.OnlineLearningSystem.entities.class_setting.service.ClassSettingService;
import swp490.g7.OnlineLearningSystem.utilities.Constants;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/class-setting")
public class ClassSettingController {

    @Autowired
    ClassSettingService classSettingService;

    @GetMapping("/all")
    public PaginationResponse findAll(Pageable pageable) {
        return classSettingService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ClassSettingResponseDto findById(@PathVariable("id") Long id) {
        return classSettingService.findById(id);
    }

    @UserPermission(name = Constants.SETTING_DETAILS, type = Constants.ADD)
    @PostMapping("")
    public ClassSettingResponseDto create(@Valid @RequestBody ClassSettingRequestDto request) {
        return classSettingService.save(request);
    }

    @UserPermission(name = Constants.SETTING_DETAILS, type = Constants.UPDATE)
    @PutMapping("/{id}")
    public ClassSettingResponseDto update(@PathVariable("id") Long id, @Valid @RequestBody ClassSettingRequestDto request) {
        return classSettingService.update(id, request);
    }

    @UserPermission(name = Constants.SETTING_DETAILS, type = Constants.UPDATE)
    @PutMapping("/{id}/enable")
    public void enable(@PathVariable("id") Long id) {
        classSettingService.enable(id);
    }

    @UserPermission(name = Constants.SETTING_DETAILS, type = Constants.UPDATE)
    @PutMapping("/{id}/disable")
    public void disable(@PathVariable("id") Long id) {
        classSettingService.disable(id);
    }

    @GetMapping("/filter")
    public PaginationResponse filter(@RequestParam("subjectId") Long subjectId,
                                     @RequestParam(required = false) Long typeId,
                                     @RequestParam(required = false) Boolean status,
                                     @RequestParam(required = false) String settingTitle,
                                     @RequestParam(required = false) String classCode,
                                     Pageable pageable) {
        return classSettingService.filter(subjectId, typeId, status, settingTitle, classCode, pageable);
    }

    @GetMapping("/class-module")
    public List<ClassSettingResponseDto> getAllByClassModule(@RequestParam("subjectId") Long subjectId) {
        return classSettingService.getAllByClassModule(subjectId);
    }
}
