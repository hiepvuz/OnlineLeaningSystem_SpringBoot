package swp490.g7.OnlineLearningSystem.entities.classroom.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class TraineeDto {
    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("classCode")
    private String classCode;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("status")
    private Boolean status;
}
