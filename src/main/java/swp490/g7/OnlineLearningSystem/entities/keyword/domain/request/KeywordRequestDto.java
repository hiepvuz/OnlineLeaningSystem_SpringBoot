package swp490.g7.OnlineLearningSystem.entities.keyword.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KeywordRequestDto {
    @JsonProperty("keyword")
    String keyword;

    @JsonProperty("excerpt")
    String excerpt;

    @JsonProperty("body")
    String body;

    @JsonProperty("status")
    Boolean status;

    @JsonProperty("categoryId")
    Long categoryId;

    @JsonProperty("contentGroupIds")
    List<Long> contentGroupIds;
}
