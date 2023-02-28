package swp490.g7.OnlineLearningSystem.entities.subject_setting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.SubjectSetting;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectSettingRepository extends JpaRepository<SubjectSetting, Long>, SubjectSettingRepositoryCustom {
    Optional<SubjectSetting> findBySubjectSettingId(Long subjectId);

    @Query("SELECT ss.subjectSettingTitle FROM SubjectSetting ss WHERE ss.subjectSettingId = ?1")
    String getSubjectSettingTitleBySubjectSettingId(Long subjectId);

    @Query("SELECT ss.status FROM SubjectSetting ss WHERE ss.subjectSettingId = ?1")
    Boolean getStatusSubjectSettingBySubjectSettingId(Long subjectSettingId);

    List<SubjectSetting> findBySubjectSettingIdIn(List<Long> subjectSettingIds);
}
