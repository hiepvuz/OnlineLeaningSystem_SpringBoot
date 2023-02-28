package swp490.g7.OnlineLearningSystem.entities.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import swp490.g7.OnlineLearningSystem.annotations.UserPermission;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.request.ChangePasswordRequest;
import swp490.g7.OnlineLearningSystem.entities.user.domain.request.UserRequestDto;
import swp490.g7.OnlineLearningSystem.entities.user.domain.response.UserPermissionResponseDto;
import swp490.g7.OnlineLearningSystem.entities.user.domain.response.UserResponseDto;
import swp490.g7.OnlineLearningSystem.entities.user.services.UserService;
import swp490.g7.OnlineLearningSystem.utilities.Constants;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping("/user-name")
    public UserResponseDto getByUsername(@PathVariable String Username) {
        return userService.findUserByUsername(Username);
    }

    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable("id") Long id, @Valid @RequestBody UserRequestDto userRequestDto) {
        return userService.update(id, userRequestDto);
    }

    @UserPermission(name = Constants.USER_DETAILS, type = Constants.DELETE)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
    }

    @PutMapping("/{id}/disable")
    public void disable(@PathVariable("id") Long id) {
        userService.disable(id);
    }

    @PutMapping("/{id}/change-password")
    public void changePassword(@PathVariable("id") Long id, @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(id, request);
    }

    @GetMapping("/current-user")
    public UserResponseDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    @UserPermission(name = Constants.USER_LIST, type = Constants.ALL)
    @GetMapping("/all")
    public PaginationResponse<Object> findAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @PutMapping("/{id}/enable")
    public void enable(@PathVariable("id") Long id) {
        userService.enable(id);
    }

    @GetMapping("/current-user-permission")
    public UserPermissionResponseDto getCurrentUserPermission() {
        return userService.getCurrentUserPermission();
    }

    @UserPermission(name = Constants.USER_LIST, type = Constants.ALL)
    @GetMapping("/filter")
    public PaginationResponse filter(@RequestParam(required = false) String role,
                                     @RequestParam(required = false) Boolean disabled,
                                     @RequestParam(required = false) String username,
                                     @RequestParam(required = false) String phone,
                                     Pageable pageable) {
        return userService.filter(role, disabled, username, phone, pageable);
    }

    @GetMapping("/find-by-role")
    public List<Object> findListUserByRole(@RequestParam String displayOrder) {
        return userService.findListUserByRole(displayOrder);
    }

    @GetMapping("/all-supporter")
    public Set<Object> getAllManager() {
        return userService.getAllSupporter();
    }

    @GetMapping("/all-trainer")
    public Set<Object> getAllTrainer() {
        return userService.getAllTrainer();
    }

    @PutMapping("{id}/image-upload")
    public void imageUpload(@PathVariable Long id, @RequestPart(value = "file") MultipartFile file) {
        userService.imageUpload(id, file);
    }
}

