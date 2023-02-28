package swp490.g7.OnlineLearningSystem.entities.question.domain.dto;

import com.poiji.annotation.ExcelCellName;
import lombok.Data;

@Data
public class ExcelQuestionDto {

    @ExcelCellName("questionId")
    Long questionId;

    @ExcelCellName("subjectId")
    Long subjectId;

    @ExcelCellName("body")
    String body;

    @ExcelCellName("testId")
    Long testId;

    @ExcelCellName("imageName")
    String imageUrl;

    @ExcelCellName("isKey")
    String isKey;

    @ExcelCellName("contentGroupIds")
    String contentGroupIds;

    @ExcelCellName("testType")
    Long testType;

    @ExcelCellName("answer1")
    String answerOption1;

    @ExcelCellName("answer2")
    String answerOption2;

    @ExcelCellName("answer3")
    String answerOption3;

    @ExcelCellName("answer4")
    String answerOption4;

    @ExcelCellName("answer5")
    String answerOption5;

    @ExcelCellName("answer6")
    String answerOption6;

    @ExcelCellName("explanation")
    String explanation;

    @ExcelCellName("source")
    String source;

    @ExcelCellName("page")
    String page;
}
