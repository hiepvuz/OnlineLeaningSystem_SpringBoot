package swp490.g7.OnlineLearningSystem.entities.user_test.service;

import org.springframework.data.domain.Pageable;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.other.TestHistoryDto;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

public interface UserTestService {
    PaginationResponse filter(Long subjectId, Long userId, Integer testType, String testName, Pageable pageable);

    TestHistoryDto findById(Long id);
}
