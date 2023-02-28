package swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle;

import lombok.Data;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.TestQuestionRandomDto;

import java.util.List;

@Data
public class TestResultReview {
    Long testId;

    Integer testType;

    Integer numberCorrect;

    Double scorePercent;

    Integer totalOfQuestion;

    Double totalTime;

    Double secondPerQuestion;

    Boolean isPass;

    String configType;

    String moduleName;

    List<TestQuestionRandomDto> questionResult;

    List<ResultAnalysisReview> resultAnalysis;
}
