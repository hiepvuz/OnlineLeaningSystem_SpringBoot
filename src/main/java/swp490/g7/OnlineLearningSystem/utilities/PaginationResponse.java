package swp490.g7.OnlineLearningSystem.utilities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
@Builder
public class PaginationResponse<T> {

    @JsonProperty("total")
    private long total;

    @JsonProperty("numberOfPage")
    private int numberOfPage;

    @JsonProperty("pageIndex")
    private int pageIndex;

    @JsonProperty("items")
    private List<T> items;
}
