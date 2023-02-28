package swp490.g7.OnlineLearningSystem.entities.class_setting.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class ClassSettingRequestDto {

    @Nullable
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
}
