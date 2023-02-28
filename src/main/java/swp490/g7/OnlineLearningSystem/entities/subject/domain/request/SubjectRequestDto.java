package swp490.g7.OnlineLearningSystem.entities.subject.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectRequestDto {

    @JsonProperty("subjectCode")
    private String subjectCode;

    @JsonProperty("subjectName")
    private String subjectName;

    @JsonProperty("categoryId")
    private Long categoryId;

    @JsonProperty("managerId")
    private Long managerId;

    @JsonProperty("expertId")
    private Long expertId;

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("body")
    private String body;
}
