package swp490.g7.OnlineLearningSystem.entities.subject_setting.repository.impl;

import org.apache.commons.lang3.ObjectUtils;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.response.SubjectSettingResponseDto;
import swp490.g7.OnlineLearningSystem.entities.subject_setting.repository.SubjectSettingRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class SubjectSettingRepositoryCustomImpl implements SubjectSettingRepositoryCustom {
    private static final String PATH_SETTING_RESPONSE_DTO
            = " swp490.g7.OnlineLearningSystem.entities.subject_setting.domain.response.SubjectSettingResponseDto";

    private static final String SUBJECT_SETTING_JOIN_SUBJECT_CONDITION
            = " join Subject su ON s.subjectId = su.subjectId";
    private static final String SETTING_RES_DTO_FIELD
            = " (s.subjectSettingId, s.typeId, s.subjectSettingTitle, s.settingValue, s.displayOrder, s.status," +
            " s.description, s.subjectId, su.subjectCode)";

    private static final String AND_CONDITION = " AND ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SubjectSettingResponseDto> filter(Long subjectId, Long typeId, Boolean status, String settingTitle,
                                                  String subjectCode) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_SETTING_RESPONSE_DTO);
        customQuery.append(SETTING_RES_DTO_FIELD);
        customQuery.append(" FROM SubjectSetting s");
        customQuery.append(SUBJECT_SETTING_JOIN_SUBJECT_CONDITION);
        customQuery.append(" WHERE s.subjectId = " + subjectId);

        if (ObjectUtils.isNotEmpty(typeId)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("s.typeId =:typeId");
        }
        if (ObjectUtils.isNotEmpty(status)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("s.status =:status");
        }
        if (ObjectUtils.isNotEmpty(settingTitle)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("s.subjectSettingTitle LIKE CONCAT('%', :settingTitle, '%')");
        }
        if (ObjectUtils.isNotEmpty(subjectCode)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("su.subjectCode LIKE CONCAT('%', :subjectCode, '%')");
        }

        TypedQuery<SubjectSettingResponseDto> query = entityManager.createQuery(customQuery.toString(),
                SubjectSettingResponseDto.class);

        if (ObjectUtils.isNotEmpty(typeId)) {
            query.setParameter("typeId", typeId);
        }
        if (ObjectUtils.isNotEmpty(status)) {
            query.setParameter("status", status);
        }
        if (ObjectUtils.isNotEmpty(settingTitle)) {
            query.setParameter("settingTitle", settingTitle);
        }
        if (ObjectUtils.isNotEmpty(subjectCode)) {
            query.setParameter("subjectCode", subjectCode);
        }
        return query.getResultList();
    }

    @Override
    public List<SubjectSettingResponseDto> getAllBySubjectModuleOrSubjectContent(Long subjectId, Long typeId) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_SETTING_RESPONSE_DTO);
        customQuery.append(SETTING_RES_DTO_FIELD);
        customQuery.append(" FROM SubjectSetting s");
        customQuery.append(SUBJECT_SETTING_JOIN_SUBJECT_CONDITION);
        customQuery.append(" WHERE s.subjectId = " + subjectId);
        customQuery.append(" AND s.typeId = " + typeId);
        customQuery.append(" AND s.status IS TRUE");
        return entityManager.createQuery(customQuery.toString(),
                SubjectSettingResponseDto.class).getResultList();
    }
}
