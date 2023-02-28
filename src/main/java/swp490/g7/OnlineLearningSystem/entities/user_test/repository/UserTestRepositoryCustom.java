package swp490.g7.OnlineLearningSystem.entities.user_test.repository;

import swp490.g7.OnlineLearningSystem.entities.user_test.domain.response.UserTestResponseDto;

import java.util.List;

public interface UserTestRepositoryCustom {
    List<UserTestResponseDto> filter(Long subjectId, Long userId, Integer testType, String testName);
}
