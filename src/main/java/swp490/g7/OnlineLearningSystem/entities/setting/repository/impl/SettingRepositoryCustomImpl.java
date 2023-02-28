package swp490.g7.OnlineLearningSystem.entities.setting.repository.impl;

import org.apache.commons.lang3.ObjectUtils;
import swp490.g7.OnlineLearningSystem.entities.setting.domain.response.SettingResponseDto;
import swp490.g7.OnlineLearningSystem.entities.setting.repository.SettingRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


public class SettingRepositoryCustomImpl implements SettingRepositoryCustom {
    private static final String PATH_SETTING_RESPONSE_DTO
            = " swp490.g7.OnlineLearningSystem.entities.setting.domain.response.SettingResponseDto";

    private static final String SETTING_RES_DTO_FIELD
            = " (s.settingId, s.typeId, s.settingTitle, s.settingValue, s.displayOrder, s.status, s.description)";

    private static final String AND_CONDITION = " AND ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SettingResponseDto> filter(Long typeId, Boolean status, String settingTitle) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_SETTING_RESPONSE_DTO);
        customQuery.append(SETTING_RES_DTO_FIELD);
        customQuery.append(" FROM Setting s");

        customQuery.append(" WHERE 1 = 1");
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
            customQuery.append("s.settingTitle LIKE CONCAT('%', :settingTitle, '%')");
        }

        TypedQuery<SettingResponseDto> query = entityManager.createQuery(customQuery.toString(), SettingResponseDto.class);
        if (ObjectUtils.isNotEmpty(typeId)) {
            query.setParameter("typeId", typeId);
        }
        if (ObjectUtils.isNotEmpty(status)) {
            query.setParameter("status", status);
        }
        if (ObjectUtils.isNotEmpty(settingTitle)) {
            query.setParameter("settingTitle", settingTitle);
        }
        return query.getResultList();
    }
}
