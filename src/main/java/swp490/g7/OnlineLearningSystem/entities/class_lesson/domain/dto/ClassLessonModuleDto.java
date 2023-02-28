package swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ClassLessonModuleDto {
    @JsonProperty("moduleId")
    Long moduleId;

    @JsonProperty("moduleName")
    String moduleName;

    @JsonProperty("status")
    Boolean status;

    @JsonProperty("classLessons")
    List<ClassLessonDto> classLessons;
}
