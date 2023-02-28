package swp490.g7.OnlineLearningSystem.utilities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;

@Data
@EqualsAndHashCode
public class PaginationRequest {

    @JsonProperty("offset")
    @Min(0)
    int offset = 0;

    @JsonProperty("limit")
    int limit = 5;

    @JsonProperty("fields")
    String[] fields;

    @JsonProperty("search")
    String search;
}
