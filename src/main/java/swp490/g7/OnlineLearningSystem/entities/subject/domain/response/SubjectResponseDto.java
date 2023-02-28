package swp490.g7.OnlineLearningSystem.entities.subject.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubjectResponseDto {

    @JsonProperty("subjectId")
    private Long subjectId;

    @JsonProperty("subjectCode")
    private String subjectCode;

    @JsonProperty("subjectName")
    private String subjectName;

    @JsonProperty("categoryId")
    private Long categoryId;

    @JsonProperty("managerUserName")
    private String managerUserName;

    @JsonProperty("managerId")
    private Long managerId;

    @JsonProperty("expertId")
    private Long expertId;

    @JsonProperty("expertUserName")
    private String expertUserName;

    @JsonProperty("status")
    private Boolean status;

    @JsonProperty("body")
    private String body;

    public SubjectResponseDto(Long subjectId, String subjectCode, String subjectName, Long categoryId,
                              String managerUserName, Long managerId, Long expertId, String expertUserName, Boolean status,
                              String body) {
        this.subjectId = subjectId;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.categoryId = categoryId;
        this.managerUserName = managerUserName;
        this.managerId = managerId;
        this.expertId = expertId;
        this.expertUserName = expertUserName;
        this.status = status;
        this.body = body;
    }
}
