package swp490.g7.OnlineLearningSystem.entities.classroom.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.Classroom;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.request.ClassroomRequestDto;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.response.ClassroomResponseDto;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.response.TraineeResponse;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.Lesson;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.List;

public interface ClassroomService {
    List<Classroom> findAll();

    PaginationResponse getAll(Pageable pageable);

    ClassroomResponseDto findById(Long classId);

    ClassroomResponseDto save(ClassroomRequestDto request);

    ClassroomResponseDto update(Long id, ClassroomRequestDto request);

    PaginationResponse filter(Boolean status, String fromDate, String toDate, String classCode, String trainerUserName,
                              String supporterUserName, String term, String branch, Long subjectId, Pageable pageable);

    ClassroomResponseDto getClassroomById(Long classId);

    void enable(Long id);

    void disable(Long id);

    TraineeResponse uploadFile(Long classId, MultipartFile file);

    void syncSubjectData(List<Lesson> lessons, Classroom classroom);
}
