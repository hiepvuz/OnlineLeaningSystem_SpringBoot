package swp490.g7.OnlineLearningSystem.entities.study_result.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.study_result.domain.StudyResult;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudyResultRepository extends JpaRepository<StudyResult, Long> {

    @Query("SELECT COUNT(sr) FROM StudyResult sr WHERE sr.subjectId = ?1 AND sr.userId = ?2 AND sr.lessonId IS NOT NULL" +
            " AND (sr.isCompleted IS TRUE OR sr.isPassed IS TRUE)")
    long countLessonUserCompleted(Long subjectId, Long userId);

    @Query("SELECT sr FROM StudyResult sr WHERE sr.subjectId = ?1 AND sr.userId = ?2 AND sr.isPassed IS NOT NULL AND" +
            " sr.lessonResult IS NOT NULL AND sr.classLessonId IS NULL")
    List<StudyResult> getLessonQuizStudyResultByUserIdAndSubjectId(Long subjectId, Long userId);

    @Query("SELECT sr FROM StudyResult sr WHERE sr.subjectId = ?1 AND sr.userId = ?2 AND sr.isPassed IS NOT NULL AND" +
            " sr.lessonResult IS NOT NULL AND sr.lessonId IS NULL")
    List<StudyResult> getClassLessonQuizStudyResultByUserIdAndSubjectId(Long subjectId, Long userId);

    Optional<StudyResult> findBySubjectIdAndLessonIdAndUserId(Long subjectId, Long lessonId, Long userId);

    Optional<StudyResult> findBySubjectIdAndClassLessonIdAndUserId(Long subjectId, Long classLessonId, Long userId);
}
