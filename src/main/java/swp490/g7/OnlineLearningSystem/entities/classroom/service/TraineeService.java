package swp490.g7.OnlineLearningSystem.entities.classroom.service;

import org.springframework.data.domain.Pageable;
import swp490.g7.OnlineLearningSystem.entities.user.domain.User;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.List;
import java.util.Optional;

public interface TraineeService {
    PaginationResponse findAllTrainee(Long classId, Boolean status, Pageable pageable);

    List<User> findTraineeByEmailIn(List<String> emails);

    Optional<User> findTraineeNotInClass(String email, Long classId);
}
