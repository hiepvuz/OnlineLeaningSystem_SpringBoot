package swp490.g7.OnlineLearningSystem.entities.lesson.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class LessonModuleDto {
    @JsonProperty("moduleId")
    Long moduleId;

    @JsonProperty("moduleName")
    String moduleName;

    @JsonProperty("status")
    Boolean status;

    @JsonProperty("lessons")
    List<LessonDto> lessons;
}
