package swp490.g7.OnlineLearningSystem.entities.user_test.repository.impl;

import org.apache.commons.lang3.ObjectUtils;
import swp490.g7.OnlineLearningSystem.entities.user_test.domain.response.UserTestResponseDto;
import swp490.g7.OnlineLearningSystem.entities.user_test.repository.UserTestRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserTestRepositoryCustomImpl implements UserTestRepositoryCustom {

    private static final String PATH_USER_TEST_RESPONSE_DTO
            = " swp490.g7.OnlineLearningSystem.entities.user_test.domain.response.UserTestResponseDto";

    private static final String USER_TEST_RES_DTO
            = " (ut.userTestId, ut.test.testId, ut.test.name, ut.test.duration, ut.test.passRate, ut.total," +
            " ut.corrects, ut.startedDate, ut.result)";

    private static final String AND_CONDITION = " AND ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserTestResponseDto> filter(Long subjectId, Long userId, Integer testType, String testName) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_USER_TEST_RESPONSE_DTO);
        customQuery.append(USER_TEST_RES_DTO);
        customQuery.append(" FROM UserTest ut");
        customQuery.append(" WHERE ut.userId =:userId AND ut.test.subject.subjectId =:subjectId");

        if (ObjectUtils.isNotEmpty(testType)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("ut.test.testType =:testType");
        }
        if (ObjectUtils.isNotEmpty(testName)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("ut.test.name LIKE CONCAT('%', :testName, '%')");
        }
        TypedQuery<UserTestResponseDto> query = entityManager.createQuery(customQuery.toString(), UserTestResponseDto.class);
        query.setParameter("userId", userId);
        query.setParameter("subjectId", subjectId);
        if (ObjectUtils.isNotEmpty(testType)) {
            query.setParameter("testType", testType);
        }
        if (ObjectUtils.isNotEmpty(testName)) {
            query.setParameter("testName", testName);
        }
        return query.getResultList();
    }
}
