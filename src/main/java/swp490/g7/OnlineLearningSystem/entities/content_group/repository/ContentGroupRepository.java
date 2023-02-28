package swp490.g7.OnlineLearningSystem.entities.content_group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.ContentGroup;

import java.util.Collection;
import java.util.List;

@Repository
public interface ContentGroupRepository extends JpaRepository<ContentGroup, Long>, ContentGroupRepositoryCustom {

    List<ContentGroup> findByGroupIdIn(Collection<Long> groupIds);

    @Query("SELECT c FROM ContentGroup c JOIN SubjectSetting ss ON c.typeId = ss.subjectSettingId WHERE ss.subjectId = ?1 AND c.typeId = ?2")
    List<ContentGroup> getBySubjectIdTypeId(Long subjectId, Long typeId);

    @Query("SELECT c FROM ContentGroup c JOIN SubjectSetting ss ON c.typeId = ss.subjectSettingId WHERE ss.subjectId = ?1")
    List<ContentGroup> getBySubjectId(Long subjectId);

    @Query("SELECT c.name FROM ContentGroup c WHERE c.groupId = ?1")
    String getNameContentGroupById(Long groupId);

    @Query("SELECT c.typeId FROM ContentGroup c WHERE c.groupId = ?1")
    long getTypeIdByContentGroupId(Long groupId);
}
