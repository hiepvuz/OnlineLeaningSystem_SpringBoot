package swp490.g7.OnlineLearningSystem.entities.permission.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swp490.g7.OnlineLearningSystem.entities.permission.domain.response.PermissionResponseDto;
import swp490.g7.OnlineLearningSystem.entities.permission.repository.PermissionRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class PermissionRepositoryCustomImpl implements PermissionRepositoryCustom {
    private static final Logger logger = LogManager.getLogger(PermissionRepositoryCustomImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    private static final String PATH_PERMISSION_RESPONSE_DTO
            = " swp490.g7.OnlineLearningSystem.entities.permission.domain.response.PermissionResponseDto";

    private static final String PERMISSION_RESPONSE_FIELD
            = " (p.permissionId.roleId, p.permissionId.screenId, p.allData, p.canAdd, p.canDelete, p.canEdit, s1.settingTitle, s2.settingTitle)";

    private static final String AND_CONDITION = " AND ";

    @Override
    public List<PermissionResponseDto> getAll() {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_PERMISSION_RESPONSE_DTO);
        customQuery.append(PERMISSION_RESPONSE_FIELD);
        customQuery.append(" FROM Permission p");
        customQuery.append(" LEFT JOIN Setting s1 ON p.permissionId.screenId = s1.settingId");
        customQuery.append(" LEFT JOIN Setting s2 ON p.permissionId.roleId = s2.settingId");
        return entityManager.createQuery(customQuery.toString(), PermissionResponseDto.class).getResultList();
    }

    @Override
    public List<PermissionResponseDto> getByRole(Long roleId) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_PERMISSION_RESPONSE_DTO);
        customQuery.append(PERMISSION_RESPONSE_FIELD);
        customQuery.append(" FROM Permission p");
        customQuery.append(" LEFT JOIN Setting s1 ON p.permissionId.screenId = s1.settingId");
        customQuery.append(" LEFT JOIN Setting s2 ON p.permissionId.roleId = s2.settingId");
        customQuery.append(" WHERE p.permissionId.roleId =:roleId");
        return entityManager.createQuery(customQuery.toString(), PermissionResponseDto.class)
                .setParameter("roleId", roleId)
                .getResultList();
    }

    @Override
    public List<PermissionResponseDto> getByScreen(Long screenId) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_PERMISSION_RESPONSE_DTO);
        customQuery.append(PERMISSION_RESPONSE_FIELD);
        customQuery.append(" FROM Permission p");
        customQuery.append(" LEFT JOIN Setting s1 ON p.permissionId.screenId = s1.settingId");
        customQuery.append(" LEFT JOIN Setting s2 ON p.permissionId.roleId = s2.settingId");
        customQuery.append(" WHERE p.permissionId.screenId =:screenId");
        return entityManager.createQuery(customQuery.toString(), PermissionResponseDto.class)
                .setParameter("screenId", screenId)
                .getResultList();
    }
}
