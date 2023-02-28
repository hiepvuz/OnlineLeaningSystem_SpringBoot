package swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultTestDto {

    List<TestQuestionRandomDto> questionResult;

    Integer numberCorrect;

    double scorePercent;

    Integer totalOfQuestion;

    double totalTime;

    double secondPerQuestion;

    Boolean isPass;

    Map<Long, Map<String, Double>> resultAnalysis;

    public ResultTestDto(List<TestQuestionRandomDto> questionResult, Integer numberCorrect, double scorePercent) {
        this.questionResult = questionResult;
        this.numberCorrect = numberCorrect;
        this.scorePercent = scorePercent;
    }
}
