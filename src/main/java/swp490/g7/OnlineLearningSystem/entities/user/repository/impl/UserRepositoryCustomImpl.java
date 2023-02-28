package swp490.g7.OnlineLearningSystem.entities.user.repository.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Pageable;
import swp490.g7.OnlineLearningSystem.entities.user.domain.User;
import swp490.g7.OnlineLearningSystem.entities.user.domain.response.UserPermissionResponseDto;
import swp490.g7.OnlineLearningSystem.entities.user.repository.UserRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private static final String PATH_USER_DTO = " swp490.g7.OnlineLearningSystem.entities.user.domain.User";

    private static final String USER_DTO_FIELD = " (u.userId, u.email, u.fullName, u.address, u.username," +
            "u.avatarUrl, u.phoneNumber, u.disabled, u.createdDate,u.updatedDate, u.roles)";
    private static final String AND_CONDITION = " AND ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserPermissionResponseDto getUserPermission() {
        return null;
    }

    @Override
    public List<User> filter(String role, Boolean disabled, String username, String phone, Pageable pageable) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT u");
        customQuery.append(" FROM User u");
        customQuery.append(" WHERE 1 = 1");
        if (ObjectUtils.isNotEmpty(disabled)) {
            customQuery.append(AND_CONDITION);
            customQuery.append(" u.disabled = :disabled");
        }
        if (ObjectUtils.isNotEmpty(username)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("u.username LIKE CONCAT('%', :username, '%')");
        }
        if (ObjectUtils.isNotEmpty(phone)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("u.phoneNumber LIKE CONCAT('%', :phoneNumber, '%')");
        }

        Query query = entityManager.createQuery(customQuery.toString());

        if (ObjectUtils.isNotEmpty(disabled)) {
            query.setParameter("disabled", disabled);
        }
        if (ObjectUtils.isNotEmpty(username)) {
            query.setParameter("username", username);
        }
        if (ObjectUtils.isNotEmpty(phone)) {
            query.setParameter("phoneNumber", phone);
        }
        return query.getResultList();
    }
}
