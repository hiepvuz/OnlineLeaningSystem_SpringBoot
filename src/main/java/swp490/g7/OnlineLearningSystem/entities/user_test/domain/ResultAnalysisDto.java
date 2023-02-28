package swp490.g7.OnlineLearningSystem.entities.user_test.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultAnalysisDto {
    Long id;
    String name;
    Double percent;
}
