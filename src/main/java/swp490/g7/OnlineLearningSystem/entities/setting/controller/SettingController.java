package swp490.g7.OnlineLearningSystem.entities.setting.controller;

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
import swp490.g7.OnlineLearningSystem.entities.setting.domain.request.SettingRequestDto;
import swp490.g7.OnlineLearningSystem.entities.setting.domain.response.SettingResponseDto;
import swp490.g7.OnlineLearningSystem.entities.setting.service.SettingService;
import swp490.g7.OnlineLearningSystem.utilities.Constants;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/setting")
public class SettingController {

    @Autowired
    private SettingService settingService;

    @GetMapping("/{id}")
    public SettingResponseDto findById(@PathVariable("id") Long id) {
        return settingService.findById(id);
    }

    @GetMapping("/all")
    public PaginationResponse<Object> findAll(Pageable pageable) {
        return settingService.findAll(pageable);
    }

    @UserPermission(name = Constants.SETTING_DETAILS, type = Constants.ADD)
    @PostMapping("")
    public SettingResponseDto create(@Valid @RequestBody SettingRequestDto request) {
        return settingService.create(request);
    }

    @UserPermission(name = Constants.SETTING_DETAILS, type = Constants.UPDATE)
    @PutMapping("/{id}")
    public SettingResponseDto update(@PathVariable("id") Long id, @Valid @RequestBody SettingRequestDto request) {
        return settingService.update(id, request);
    }

    @GetMapping("/all-role")
    public Set<String> allRole() {
        return settingService.findAllRole();
    }

    @GetMapping("/{id}/type-id")
    public PaginationResponse findByTypeId(@PathVariable("id") Long id, Pageable pageable) {
        return settingService.findByTypeId(id, pageable);
    }

    @UserPermission(name = Constants.SETTING_DETAILS, type = Constants.ALL)
    @GetMapping("/filter")
    public PaginationResponse filter(@RequestParam(required = false) Long typeId,
                                     @RequestParam(required = false) Boolean status,
                                     @RequestParam(required = false) String settingTitle,
                                     Pageable pageable) {
        return settingService.filter(typeId, status, settingTitle, pageable);
    }

    @UserPermission(name = Constants.SETTING_DETAILS, type = Constants.UPDATE)
    @PutMapping("/{id}/enable")
    public void enable(@PathVariable("id") Long id) {
        settingService.enable(id);
    }

    @UserPermission(name = Constants.SETTING_DETAILS, type = Constants.UPDATE)
    @PutMapping("/{id}/disable")
    public void disable(@PathVariable("id") Long id) {
        settingService.disable(id);
    }
}
