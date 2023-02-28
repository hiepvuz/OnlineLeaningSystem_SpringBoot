package swp490.g7.OnlineLearningSystem.entities.subject.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SubjectHeaderResponse {
    @JsonProperty("subjectId")
    private Long subjectId;

    @JsonProperty("subjectCode")
    private String subjectCode;

    @JsonProperty("subjectName")
    private String subjectName;
}
