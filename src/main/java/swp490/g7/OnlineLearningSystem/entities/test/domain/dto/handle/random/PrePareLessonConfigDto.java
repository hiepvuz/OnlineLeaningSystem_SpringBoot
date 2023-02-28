package swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random;

import lombok.Data;

import java.util.List;

@Data
public class PrePareLessonConfigDto {
    List<QuizLessonTotal> lessonTotals;
    List<ContentGroupTotal> contentGroupTotals;
    Long subjectId;
}

