package swp490.g7.OnlineLearningSystem.entities.subject.repository;

import swp490.g7.OnlineLearningSystem.entities.subject.domain.response.SubjectResponseDto;

import java.util.List;

public interface SubjectRepositoryCustom {

    List<SubjectResponseDto> getAll();

    SubjectResponseDto getSubjectById(Long subjectId);

    List<SubjectResponseDto> filter(String subjectName, String subjectCode, Boolean status);
}
