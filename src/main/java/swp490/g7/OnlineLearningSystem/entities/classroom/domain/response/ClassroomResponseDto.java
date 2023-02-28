package swp490.g7.OnlineLearningSystem.entities.classroom.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class ClassroomResponseDto {

    @JsonProperty("classId")
    Long classId;

    @JsonProperty("classCode")
    String classCode;

    @JsonProperty("description")
    String description;

    @JsonProperty("fromDate")
    Date fromDate;

    @JsonProperty("toDate")
    Date toDate;

    @JsonProperty("status")
    Boolean status;

    @JsonProperty("branch")
    String branch;

    @JsonProperty("term")
    String term;

    @JsonProperty("trainerId")
    Long trainerId;

    @JsonProperty("trainerUserName")
    String trainerUserName;

    @JsonProperty("supporterId")
    Long supporterId;

    @JsonProperty("supporterUserName")
    String supporterUserName;

    @JsonProperty("subjectId")
    Long subjectId;

    public ClassroomResponseDto(Long classId, String classCode, String description, Date fromDate, Date toDate,
                                Boolean status, String branch, String term, Long trainerId, String trainerUserName,
                                Long supporterId, String supporterUserName, Long subjectId) {
        this.classId = classId;
        this.classCode = classCode;
        this.description = description;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.status = status;
        this.branch = branch;
        this.term = term;
        this.trainerId = trainerId;
        this.trainerUserName = trainerUserName;
        this.supporterId = supporterId;
        this.supporterUserName = supporterUserName;
        this.subjectId = subjectId;
    }
}
