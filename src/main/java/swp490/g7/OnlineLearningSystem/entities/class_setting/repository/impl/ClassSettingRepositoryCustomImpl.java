package swp490.g7.OnlineLearningSystem.entities.class_setting.repository.impl;

import org.apache.commons.lang3.ObjectUtils;
import swp490.g7.OnlineLearningSystem.entities.class_setting.domain.response.ClassSettingResponseDto;
import swp490.g7.OnlineLearningSystem.entities.class_setting.repository.ClassSettingRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class ClassSettingRepositoryCustomImpl implements ClassSettingRepositoryCustom {
    private static final String PATH_SETTING_RESPONSE_DTO
            = " swp490.g7.OnlineLearningSystem.entities.class_setting.domain.response.ClassSettingResponseDto";

    private static final String CLASS_JOIN_CLASS_CONDITION
            = " LEFT JOIN Classroom c ON s.classId = c.classId";

    private static final String SETTING_RES_DTO_FIELD
            = " (s.classSettingId, s.typeId, s.classId, s.classSettingTitle, s.settingValue, s.displayOrder," +
            " s.status, s.description, c.classCode, c.subjectId)";

    private static final String AND_CONDITION = " AND ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ClassSettingResponseDto> filter(Long subjectId, Long typeId, Boolean status, String settingTitle, String classCode) {

        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_SETTING_RESPONSE_DTO);
        customQuery.append(SETTING_RES_DTO_FIELD);
        customQuery.append(" FROM ClassSetting s");
        customQuery.append(CLASS_JOIN_CLASS_CONDITION);
        customQuery.append(" WHERE c.subjectId = " + subjectId);

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
            customQuery.append("s.classSettingTitle LIKE CONCAT('%', :settingTitle, '%')");
        }
        if (ObjectUtils.isNotEmpty(classCode)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("c.classCode LIKE CONCAT('%', :classCode, '%')");
        }

        TypedQuery<ClassSettingResponseDto> query = entityManager.createQuery(customQuery.toString(),
                ClassSettingResponseDto.class);

        if (ObjectUtils.isNotEmpty(typeId)) {
            query.setParameter("typeId", typeId);
        }
        if (ObjectUtils.isNotEmpty(status)) {
            query.setParameter("status", status);
        }
        if (ObjectUtils.isNotEmpty(settingTitle)) {
            query.setParameter("settingTitle", settingTitle);
        }
        if (ObjectUtils.isNotEmpty(classCode)) {
            query.setParameter("classCode", classCode);
        }
        return query.getResultList();
    }
}
