package swp490.g7.OnlineLearningSystem.entities.user.services;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import swp490.g7.OnlineLearningSystem.auth.domain.payload.request.ChangePasswordRequest;
import swp490.g7.OnlineLearningSystem.entities.user.domain.User;
import swp490.g7.OnlineLearningSystem.entities.user.domain.request.UserRequestDto;
import swp490.g7.OnlineLearningSystem.entities.user.domain.response.UserPermissionResponseDto;
import swp490.g7.OnlineLearningSystem.entities.user.domain.response.UserResponseDto;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    UserResponseDto findUserByUsername(String userName);

    UserResponseDto findById(Long userId);

    UserResponseDto create(UserRequestDto userRequestDto);

    UserResponseDto update(Long id, UserRequestDto userRequestDto);

    void deleteById(Long id);

    void disable(Long userId);

    void changePassword(Long id, ChangePasswordRequest request);

    UserResponseDto getCurrentUser();

    PaginationResponse<Object> findAll(Pageable pageable);

    void enable(Long userId);

    void newPassword(String email, String newPassword);

    UserPermissionResponseDto getCurrentUserPermission();

    Optional<User> findByUsername(String userName);

    PaginationResponse filter(String role, Boolean disabled, String username, String phone, Pageable pageable);

    List<Object> findListUserByRole(String displayOrder);

    List<User> findByEmailIn(List<String> emails);

    void imageUpload(Long id, MultipartFile file);

    Set<Object> getAllSupporter();

    Set<Object> getAllTrainer();

    Set<String> getEmails();
}

