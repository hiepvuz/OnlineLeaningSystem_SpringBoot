package swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContentGroupExportDto {

    @JsonProperty("groupId")
    private Long groupId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    public ContentGroupExportDto(Long groupId, String name, String description) {
        this.groupId = groupId;
        this.name = name;
        this.description = description;
    }
}
