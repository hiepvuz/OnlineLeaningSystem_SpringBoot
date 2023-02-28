package swp490.g7.OnlineLearningSystem.entities.keyword.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KeywordResponseDto {

    @JsonProperty("keywordId")
    Long keywordId;

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

    @JsonProperty("subjectSettingTitle")
    String subjectSettingTitle;
}
