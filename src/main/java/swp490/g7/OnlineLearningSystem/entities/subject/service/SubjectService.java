package swp490.g7.OnlineLearningSystem.entities.subject.service;

import org.springframework.data.domain.Pageable;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.request.SubjectRequestDto;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.response.SubjectHeaderResponse;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.response.SubjectResponseDto;
import swp490.g7.OnlineLearningSystem.utilities.PaginationResponse;

import java.util.List;
import java.util.Set;

public interface SubjectService {

    PaginationResponse getAll(Pageable pageable);

    SubjectResponseDto create(SubjectRequestDto subjectResponseDto);

    SubjectResponseDto getById(Long id);

    void deleteById(Long id);

    SubjectResponseDto update(Long id, SubjectRequestDto subjectRequestDto);

    Set<Object> getAllManager();

    Set<Object> getAllExpert();

    PaginationResponse filter(String subjectName, String subjectCode, Boolean status, Pageable pageable);

    void enable(Long id);

    void disable(Long id);

    List<SubjectHeaderResponse> getSubjectByRole();
}
