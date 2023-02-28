package swp490.g7.OnlineLearningSystem.entities.packages.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PackResponseDto {
    @JsonProperty("packageId")
    Long packageId;

    @JsonProperty("title")
    String title;

    @JsonProperty("duration")
    String duration;

    @JsonProperty("thumbnailUrl")
    String thumbnailUrl;

    @JsonProperty("subjectId")
    Long subjectId;

    @JsonProperty("description")
    String description;

    @JsonProperty("category")
    String category;
}
