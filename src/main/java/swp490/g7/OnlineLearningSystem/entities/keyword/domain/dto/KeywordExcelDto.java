package swp490.g7.OnlineLearningSystem.entities.keyword.domain.dto;

import com.poiji.annotation.ExcelCellName;
import lombok.Data;

@Data
public class KeywordExcelDto {
    @ExcelCellName("keyword")
    String keyword;

    @ExcelCellName("excerpt")
    String excerpt;

    @ExcelCellName("body")
    String body;

    @ExcelCellName("groupId")
    String groupId;
}
