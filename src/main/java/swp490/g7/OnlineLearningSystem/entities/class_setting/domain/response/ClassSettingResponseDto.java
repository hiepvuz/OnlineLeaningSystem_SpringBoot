package swp490.g7.OnlineLearningSystem.entities.class_setting.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassSettingResponseDto {

    @JsonProperty("settingId")
    Long settingId;

    @JsonProperty("typeId")
    Long typeId;

    @JsonProperty("classId")
    Long classId;

    @JsonProperty("classSettingTitle")
    String classSettingTitle;

    @JsonProperty("settingValue")
    String settingValue;

    @JsonProperty("displayOrder")
    String displayOrder;

    @JsonProperty("status")
    Boolean status;

    @JsonProperty("description")
    String description;

    @JsonProperty("classCode")
    String classCode;

    @JsonProperty("subjectId")
    Long subjectId;

    public ClassSettingResponseDto(Long settingId, Long typeId, Long classId, String classSettingTitle,
                                   String settingValue, String displayOrder, Boolean status, String description,
                                   String classCode, Long subjectId) {
        this.settingId = settingId;
        this.typeId = typeId;
        this.classId = classId;
        this.classSettingTitle = classSettingTitle;
        this.settingValue = settingValue;
        this.displayOrder = displayOrder;
        this.status = status;
        this.description = description;
        this.classCode = classCode;
        this.subjectId = subjectId;
    }
}
