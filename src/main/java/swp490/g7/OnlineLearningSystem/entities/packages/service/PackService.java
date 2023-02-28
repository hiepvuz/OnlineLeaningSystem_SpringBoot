package swp490.g7.OnlineLearningSystem.entities.packages.service;

import swp490.g7.OnlineLearningSystem.entities.packages.domain.Pack;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.dto.GradesClassDto;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.dto.GradesDto;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.dto.LearningProgressDto;
import swp490.g7.OnlineLearningSystem.entities.packages.domain.response.PackResponseDto;

import java.util.List;

public interface PackService {
    List<Pack> findAll();

    LearningProgressDto getLearningProgress(Long subjectId);

    List<GradesDto> getGradeLessons(Long subjectId);

    List<PackResponseDto> getCurrentPackageByUser(Long id);

    List<GradesClassDto> getGradeClassLessons(Long subjectId);
}
