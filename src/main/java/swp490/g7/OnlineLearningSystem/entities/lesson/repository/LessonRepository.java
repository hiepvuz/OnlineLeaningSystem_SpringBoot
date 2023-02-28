package swp490.g7.OnlineLearningSystem.entities.lesson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.Lesson;

import java.util.List;
import java.util.Set;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long>, LessonRepositoryCustom {

    @Query("SELECT COUNT(l) FROM Lesson l LEFT JOIN SubjectSetting ss ON l.moduleId = ss.subjectSettingId " +
            "WHERE l.subject.subjectId = ?1 AND ss.status IS TRUE")
    long countTotalLessonBySubjectId(Long subjectId);

    @Query("SELECT COUNT(L) FROM Lesson l WHERE l.subject.subjectId = ?1 AND l.typeLesson = ?2")
    long countTotalLessonBySubjectIdAndTypeLesson(Long subjectId, String typeLesson);

    List<Lesson> findByLessonIdIn(Set<Long> lessonIds);

    @Query("SELECT l FROM Lesson l LEFT JOIN SubjectSetting ss ON l.moduleId = ss.subjectSettingId" +
            " WHERE l.subject.subjectId = ?1 AND l.typeLesson = 'QUIZ_LESSON' AND ss.status IS TRUE")
    List<Lesson> getLessonQuizBySubjectId(Long subjectId);

    @Query("SELECT l FROM Lesson l WHERE l.subject.subjectId = ?1 AND l.moduleId = ?2 AND l.status IS TRUE")
    List<Lesson> getLessonBySubjectIdAndModuleId(Long subjectId, Long moduleId);

    @Query("SELECT l.name FROM Lesson l WHERE l.lessonId = ?1")
    String getNameLessonById(Long lessonId);

    @Query("SELECT l FROM Lesson l WHERE l.subject.subjectId = ?1")
    List<Lesson> findAllBySubjectId(Long subjectId);

    @Query("SELECT l FROM Lesson l WHERE l.subject.subjectId = ?1 AND l.testId IS NULL AND l.status IS TRUE")
    List<Lesson> findAllNormalLessonBySubjectId(Long subjectId);

    @Query("SELECT l FROM Lesson l WHERE l.subject.subjectId = ?1 AND l.testId IS NOT NULL AND l.testId NOT IN ?2 AND l.status IS TRUE")
    List<Lesson> findAllQuizLessonBySubjectId(Long subjectId, List<Long> testIds);
}
