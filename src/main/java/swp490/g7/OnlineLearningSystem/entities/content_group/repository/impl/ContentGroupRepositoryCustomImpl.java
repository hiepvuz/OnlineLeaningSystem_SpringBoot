package swp490.g7.OnlineLearningSystem.entities.content_group.repository.impl;

import org.apache.commons.lang3.ObjectUtils;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ContentGroupExportDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.domain.response.ContentGroupResponseDto;
import swp490.g7.OnlineLearningSystem.entities.content_group.repository.ContentGroupRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class ContentGroupRepositoryCustomImpl implements ContentGroupRepositoryCustom {

    private static final String PATH_CONTENT_GROUP_RESPONSE_DTO
            = " swp490.g7.OnlineLearningSystem.entities.content_group.domain.response.ContentGroupResponseDto";

    private static final String CONTENT_GROUP_RES_DTO_FIELD
            = " (cg.groupId,cg.name, cg.description, cg.status, cg.typeId, ss.subjectSettingTitle)";

    private static final String JOIN_SUBJECT_SETTING_CONDITION
            = " LEFT JOIN SubjectSetting ss on cg.typeId = ss.subjectSettingId";

    private static final String PATH_CONTENT_GROUP_EXPORT_DTO
            = " swp490.g7.OnlineLearningSystem.entities.content_group.domain.dto.ContentGroupExportDto";

    private static final String CONTENT_GROUP_EXPORT_FIELD
            = " (c.groupId, c.name, c.description)";

    private static final String AND_CONDITION = " AND ";
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ContentGroupResponseDto getContentGroupById(Long id) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_CONTENT_GROUP_RESPONSE_DTO);
        customQuery.append(CONTENT_GROUP_RES_DTO_FIELD);
        customQuery.append(" from ContentGroup cg");
        customQuery.append(JOIN_SUBJECT_SETTING_CONDITION);
        customQuery.append(" WHERE cg.groupId =:id");
        return entityManager.createQuery(customQuery.toString(), ContentGroupResponseDto.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public List<ContentGroupResponseDto> filter(Long subjectId, String name, Long typeId, Boolean status) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_CONTENT_GROUP_RESPONSE_DTO);
        customQuery.append(CONTENT_GROUP_RES_DTO_FIELD);
        customQuery.append(" from ContentGroup cg");
        customQuery.append(JOIN_SUBJECT_SETTING_CONDITION);
        customQuery.append(" WHERE ss.status IS TRUE");
        if (ObjectUtils.isNotEmpty(subjectId)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("ss.subjectId =:subjectId");
        }
        if (ObjectUtils.isNotEmpty(typeId)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("ss.subjectSettingId =:typeId");
        }
        if (ObjectUtils.isNotEmpty(name)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("cg.name LIKE CONCAT('%', :name, '%') ");
        }
        if (ObjectUtils.isNotEmpty(status)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("cg.status =:status ");
        }
        TypedQuery<ContentGroupResponseDto> query = entityManager.createQuery(customQuery.toString(), ContentGroupResponseDto.class);

        if (ObjectUtils.isNotEmpty(subjectId)) {
            query.setParameter("subjectId", subjectId);
        }
        if (ObjectUtils.isNotEmpty(typeId)) {
            query.setParameter("typeId", typeId);
        }
        if (ObjectUtils.isNotEmpty(name)) {
            query.setParameter("name", name);
        }
        if (ObjectUtils.isNotEmpty(status)) {
            query.setParameter("status", status);
        }
        return query.getResultList();
    }

    @Override
    public List<ContentGroupResponseDto> findByTypeContent(Long id) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_CONTENT_GROUP_RESPONSE_DTO);
        customQuery.append(CONTENT_GROUP_RES_DTO_FIELD);
        customQuery.append(" from ContentGroup cg");
        customQuery.append(JOIN_SUBJECT_SETTING_CONDITION);
        customQuery.append(" where ss.subjectSettingId = :id");
        return entityManager.createQuery(customQuery.toString(), ContentGroupResponseDto.class)
                .setParameter("id", id).getResultList();
    }

    @Override
    public List<ContentGroupExportDto> getAllBySubjectIdAndTypeId(Long subjectId, Long typeId) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_CONTENT_GROUP_EXPORT_DTO);
        customQuery.append(CONTENT_GROUP_EXPORT_FIELD);
        customQuery.append(" FROM ContentGroup c");
        customQuery.append(" LEFT JOIN SubjectSetting s ON c.typeId = s.subjectSettingId");
        customQuery.append(" WHERE s.subjectId =:subjectId");
        customQuery.append(" AND s.typeId =:typeId AND c.status IS TRUE");

        TypedQuery<ContentGroupExportDto> query = entityManager.createQuery(customQuery.toString(), ContentGroupExportDto.class);
        query.setParameter("subjectId", subjectId);
        query.setParameter("typeId", typeId);
        return query.getResultList();
    }

}
