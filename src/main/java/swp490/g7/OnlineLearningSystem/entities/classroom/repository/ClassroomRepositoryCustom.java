package swp490.g7.OnlineLearningSystem.entities.classroom.repository;

import org.springframework.data.domain.Pageable;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.response.ClassroomResponseDto;

import java.util.List;

public interface ClassroomRepositoryCustom {

    List<ClassroomResponseDto> filter(Boolean status, String fromDate, String toDate, String classCode,
                                      String trainerUserName, String supporterUserName, String term,
                                      String branch, Long subjectId, Pageable pageable);

    List<ClassroomResponseDto> getAll();

    ClassroomResponseDto getClassroomById(Long id);
}
