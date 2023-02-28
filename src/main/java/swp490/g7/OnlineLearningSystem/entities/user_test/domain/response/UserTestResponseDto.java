package swp490.g7.OnlineLearningSystem.entities.user_test.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserTestResponseDto {

    @JsonProperty("userTestId")
    private Long userTestId;

    @JsonProperty("testId")
    private Long testId;

    @JsonProperty("testName")
    private String testName;

    @JsonProperty("duration")
    private Integer duration;

    @JsonProperty("passRate")
    private Integer passRate;

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("corrects")
    private Integer corrects;

    @JsonProperty("startDate")
    private Date startDate;

    @JsonProperty("result")
    private Double result;

    public UserTestResponseDto(Long userTestId, Long testId, String testName, Integer duration, Integer passRate,
                               Integer total, Integer corrects, Date startDate, Double result) {
        this.userTestId = userTestId;
        this.testId = testId;
        this.testName = testName;
        this.duration = duration;
        this.passRate = passRate;
        this.total = total;
        this.corrects = corrects;
        this.startDate = startDate;
        this.result = result;
    }
}
