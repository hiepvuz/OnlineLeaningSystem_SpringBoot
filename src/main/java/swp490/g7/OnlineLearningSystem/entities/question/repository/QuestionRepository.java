package swp490.g7.OnlineLearningSystem.entities.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.question.domain.Question;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {
    List<Question> findByQuestionIdIn(List<Long> questionIds);

    @Query("SELECT COUNT(q) FROM Question q WHERE q.lessonId = ?1")
    long countQuestionByLessonId(Long lessonId);

    @Query("SELECT COUNT(q) FROM Question q WHERE q.testId = ?1")
    long countQuestionByTestId(Long testId);

    List<Question> findQuestionByQuestionIdIn(List<Long> questionId);

    List<Question> findByTestIdAndSubject(Long testI, Subject subject);
}
