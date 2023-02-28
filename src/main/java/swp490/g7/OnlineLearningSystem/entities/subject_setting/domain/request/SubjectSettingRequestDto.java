package swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class SubjectSettingRequestDto {

    @Nullable
    @JsonProperty("typeId")
    Long typeId;

    @JsonProperty("subjectSettingTitle")
    String subjectSettingTitle;

    @JsonProperty("settingValue")
    String settingValue;

    @JsonProperty("displayOrder")
    String displayOrder;

    @JsonProperty("status")
    Boolean status;

    @JsonProperty("description")
    String description;

    @JsonProperty("subjectId")
    Long subjectId;
}
