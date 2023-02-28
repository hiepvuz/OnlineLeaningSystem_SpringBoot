package swp490.g7.OnlineLearningSystem.entities.content_group.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ContentGroupRequestDto {

    @JsonProperty("name")
    String name;

    @JsonProperty("typeId")
    Long typeId;

    @JsonProperty("description")
    String description;

    @JsonProperty("status")
    Boolean status;

}
