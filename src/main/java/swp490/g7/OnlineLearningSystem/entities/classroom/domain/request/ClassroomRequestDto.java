package swp490.g7.OnlineLearningSystem.entities.classroom.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class ClassroomRequestDto {

    @JsonProperty("classCode")
    String classCode;

    @JsonProperty("trainerId")
    Long trainerId;

    @JsonProperty("supporterId")
    Long supporterId;

    @JsonProperty("status")
    Boolean status;

    @JsonProperty("description")
    String description;

    @JsonProperty("fromDate")
    String fromDate;

    @JsonProperty("toDate")
    String toDate;

    @JsonProperty("branch")
    String branch;

    @JsonProperty("term")
    String term;

    @JsonProperty("subjectId")
    Long subjectId;
}
