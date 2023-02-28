package swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle;

import lombok.Data;

@Data
public class ResultAnalysisReview {
    Long id;

    String typeName;

    Double percent;

    Integer totalQuestion;

    Integer totalCorrects;
}
