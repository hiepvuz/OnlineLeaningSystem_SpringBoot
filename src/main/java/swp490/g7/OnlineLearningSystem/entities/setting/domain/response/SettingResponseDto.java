package swp490.g7.OnlineLearningSystem.entities.setting.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class SettingResponseDto {
    @JsonProperty("settingId")
    Long settingId;

    @JsonProperty("typeId")
    Long typeId;

    @JsonProperty("settingTitle")
    String settingTitle;

    @JsonProperty("settingValue")
    String settingValue;

    @JsonProperty("displayOrder")
    String displayOrder;

    @JsonProperty("status")
    Boolean status;

    @JsonProperty("description")
    String description;

    public SettingResponseDto(Long settingId, Long typeId, String settingTitle, String settingValue, String displayOrder, Boolean status, String description) {
        this.settingId = settingId;
        this.typeId = typeId;
        this.settingTitle = settingTitle;
        this.settingValue = settingValue;
        this.displayOrder = displayOrder;
        this.status = status;
        this.description = description;
    }


}
