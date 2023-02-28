package swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionLessonQueryDto {
    Long questionId;
    Long lessonId;

    public QuestionLessonQueryDto(Long questionId, Long lessonId) {
        this.questionId = questionId;
        this.lessonId = lessonId;
    }
}
