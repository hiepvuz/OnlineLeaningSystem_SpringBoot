package swp490.g7.OnlineLearningSystem.entities.classroom.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TraineeResponse {
    @JsonProperty("total_user_added")
    private Integer totalUserAdded;

    @JsonProperty("total_user_registered")
    private Integer totalUserRegistered;
}
