package swp490.g7.OnlineLearningSystem.entities.setting.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class SettingRequestDto {

    @Nullable
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
}
