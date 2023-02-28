package swp490.g7.OnlineLearningSystem.entities.demo_test.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DemoTestDataRequest {

    @JsonProperty("email")
    String email;

    @JsonProperty("fullName")
    String fullName;

    @JsonProperty("subjectId")
    Long subjectId;
}
