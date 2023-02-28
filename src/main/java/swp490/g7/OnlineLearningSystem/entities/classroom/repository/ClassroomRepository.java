package swp490.g7.OnlineLearningSystem.entities.classroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.Classroom;

import java.util.List;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long>, ClassroomRepositoryCustom {
    List<Classroom> findByTrainerId(Long trainerId);
}
