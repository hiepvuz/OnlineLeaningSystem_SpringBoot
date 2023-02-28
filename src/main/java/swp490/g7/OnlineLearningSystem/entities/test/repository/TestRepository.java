package swp490.g7.OnlineLearningSystem.entities.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.Subject;
import swp490.g7.OnlineLearningSystem.entities.test.domain.Test;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.PrepareLessonEntity;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long>, TestRepositoryCustom {
    List<Test> findByTestIdIn(List<Long> testId);

    @Query("SELECT t FROM Test t WHERE t.subject.subjectId = ?1 AND t.testType <> 1 AND t.testType <> 4")
    List<Test> findMockTestAndFullTestAndPracticeTestBySubjectId(Long subjectId);

    @Query(value = "SELECT \n" +
            "   count(q.question_id) as total, l.lesson_id as lessonId, l.name as name\n" +
            "FROM\n" +
            "    question q join lesson l on q.lesson_id = l.lesson_id \n" +
            "\t  join subject s on s.subject_id = l.subject_id\n" +
            "    where s.subject_id = :subjectId and l.lesson_id in :lessonIds\n" +
            "    group by l.lesson_id", nativeQuery = true)
    List<PrepareLessonEntity> getPrepareLessonEntity(@Param("subjectId") Long subjectId,
                                                     @Param("lessonIds") List<Long> lessonIds);

    List<Test> findBySubjectAndTestType(Subject subject, Integer testType);

    @Query("SELECT t.name FROM Test t WHERE t.testId = ?1")
    String getTestNameByTestId(Long testId);

    @Query("SELECT t.testId FROM Test t WHERE t.testId IN ?1")
    List<Long> findTestIdByTestIdIn(List<Long> testId);
}
