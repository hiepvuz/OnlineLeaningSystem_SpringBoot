package swp490.g7.OnlineLearningSystem.entities.class_lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.ClassLesson;

import java.util.List;

@Repository
public interface ClassLessonRepository extends JpaRepository<ClassLesson, Long>, ClassLessonRepositoryCustom {

    @Query("SELECT cl FROM ClassLesson cl LEFT JOIN ClassSetting cs ON cl.moduleId = cs.classSettingId" +
            " WHERE cl.subject.subjectId = ?1 AND cl.typeLesson = 'QUIZ_LESSON' AND cs.status IS TRUE")
    List<ClassLesson> getClassLessonQuizBySubjectId(Long subjectId);

    @Query("SELECT cl.classLessonId FROM ClassLesson cl WHERE cl.testId = ?1")
    Long findClassLessonIdByTestId(Long testId);

    @Query("SELECT cl.testId FROM ClassLesson cl WHERE cl.testId IS NOT NULL AND cl.status IS TRUE")
    List<Long> findTestIdIsTrue();
}
