package swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random;

import lombok.Data;

import java.util.List;

@Data
public class PrePareContentConfigDto {
    private List<QuizContentTotal> contentTotals;
    Long courseId;
}

@Data
class QuizContentTotal {
    Long contentId;
    String name;
    Integer total;
}