package swp490.g7.OnlineLearningSystem.entities.user.repository;

import org.springframework.data.domain.Pageable;
import swp490.g7.OnlineLearningSystem.entities.user.domain.User;
import swp490.g7.OnlineLearningSystem.entities.user.domain.response.UserPermissionResponseDto;

import java.util.List;

public interface UserRepositoryCustom {

    UserPermissionResponseDto getUserPermission();

    List<User> filter(String role, Boolean disabled, String username, String phone, Pageable pageable);

}
