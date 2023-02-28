package swp490.g7.OnlineLearningSystem.entities.classroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.dto.TraineeDto;
import swp490.g7.OnlineLearningSystem.entities.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface TraineeRepository extends JpaRepository<User, Long> {
    @Query("select new swp490.g7.OnlineLearningSystem.entities.classroom.domain.dto.TraineeDto(u.userId, c.classCode , " +
            "u.fullName , u.username ,u.email, cu.status) from Classroom c inner join ClassUser cu on c.classId = cu.classroom.classId" +
            " inner join User u on cu.user.userId = u.userId\n" +
            "where c.classId = :classId and c.status = :status")
    List<TraineeDto> findAllTrainee(Long classId, Boolean status);

    List<User> findByEmailIn(List<String> emails);

    @Query(value = "select * from users u where email = :email and user_id not in (select cu.user_id  from" +
            " class_user cu where cu.class_id = :classId);", nativeQuery = true)
    Optional<User> findTraineeNotInClass(String email, Long classId);
}