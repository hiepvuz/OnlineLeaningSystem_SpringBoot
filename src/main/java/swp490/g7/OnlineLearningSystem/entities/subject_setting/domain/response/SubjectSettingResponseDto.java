package swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectSettingResponseDto {

    @JsonProperty("subjectSettingId")
    Long subjectSettingId;

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

    @JsonProperty("subjectCode")
    String subjectCode;
}
