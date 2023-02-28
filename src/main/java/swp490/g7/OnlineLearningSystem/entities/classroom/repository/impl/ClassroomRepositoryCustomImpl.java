package swp490.g7.OnlineLearningSystem.entities.classroom.repository.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.classroom.domain.response.ClassroomResponseDto;
import swp490.g7.OnlineLearningSystem.entities.classroom.repository.ClassroomRepositoryCustom;
import swp490.g7.OnlineLearningSystem.utilities.DateLibs;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public class ClassroomRepositoryCustomImpl implements ClassroomRepositoryCustom {
    private static final Logger logger = LogManager.getLogger(ClassroomRepositoryCustomImpl.class);

    private static final String PATH_CLASSROOM_RESPONSE_DTO
            = " swp490.g7.OnlineLearningSystem.entities.classroom.domain.response.ClassroomResponseDto";

    private static final String CLASS_RES_DTO_FIELD
            = " (c.classId, c.classCode, c.description, c.fromDate, c.toDate, c.status, c.branch, c.term, c.trainerId," +
            " u.username, c.supporterId, sp.username, c.subjectId)";

    private static final String USER_JOIN_TRAINER_CONDITION
            = " left join User u ON c.trainerId = u.userId";

    private static final String USER_JOIN_SUPPORT_CONDITION
            = " left join User sp on c.supporterId = sp.userId";

    private static final String AND_CONDITION = " AND ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ClassroomResponseDto> filter(Boolean status, String fromDate, String toDate, String classCode,
                                             String trainerUserName, String supporterUserName, String term,
                                             String branch, Long subjectId, Pageable pageable) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new ");
        customQuery.append(PATH_CLASSROOM_RESPONSE_DTO);
        customQuery.append(CLASS_RES_DTO_FIELD);
        customQuery.append(" FROM Classroom c");
        customQuery.append(USER_JOIN_TRAINER_CONDITION);
        customQuery.append(USER_JOIN_SUPPORT_CONDITION);
        customQuery.append(" WHERE c.subjectId = " + subjectId);
        if (ObjectUtils.isNotEmpty(status)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("c.status =:status ");
        }
        if (ObjectUtils.isNotEmpty(fromDate)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("c.fromDate >=:fromDate ");
        }
        if (ObjectUtils.isNotEmpty(toDate)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("c.toDate <=:toDate ");
        }
        if (ObjectUtils.isNotEmpty(classCode)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("c.classCode LIKE CONCAT('%', :classCode, '%')");
        }
        if (ObjectUtils.isNotEmpty(trainerUserName)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("u.username LIKE CONCAT('%', :trainerUserName, '%')");
        }
        if (ObjectUtils.isNotEmpty(supporterUserName)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("sp.username LIKE CONCAT('%', :supporterUserName, '%')");
        }
        if (ObjectUtils.isNotEmpty(term)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("c.term LIKE CONCAT('%', :term, '%')");
        }
        if (ObjectUtils.isNotEmpty(branch)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("c.branch LIKE CONCAT('%', :branch, '%')");
        }
        TypedQuery<ClassroomResponseDto> query = entityManager.createQuery(customQuery.toString(), ClassroomResponseDto.class);

        if (ObjectUtils.isNotEmpty(status)) {
            query.setParameter("status", status);
        }
        if (ObjectUtils.isNotEmpty(fromDate)) {
            Date startDate = DateLibs.convertDate(fromDate);
            query.setParameter("fromDate", startDate);
        }
        if (ObjectUtils.isNotEmpty(toDate)) {
            Date endDate = DateLibs.convertDate(toDate);
            query.setParameter("toDate", endDate);
        }
        if (ObjectUtils.isNotEmpty(classCode)) {
            query.setParameter("classCode", classCode);
        }
        if (ObjectUtils.isNotEmpty(trainerUserName)) {
            query.setParameter("trainerUserName", trainerUserName);
        }
        if (ObjectUtils.isNotEmpty(supporterUserName)) {
            query.setParameter("supporterUserName", supporterUserName);
        }
        if (ObjectUtils.isNotEmpty(term)) {
            query.setParameter("term", term);
        }
        if (ObjectUtils.isNotEmpty(branch)) {
            query.setParameter("branch", branch);
        }
        return query.getResultList();
    }

    @Override
    public List<ClassroomResponseDto> getAll() {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_CLASSROOM_RESPONSE_DTO);
        customQuery.append(CLASS_RES_DTO_FIELD);
        customQuery.append(" FROM Classroom c");
        customQuery.append(USER_JOIN_TRAINER_CONDITION);
        customQuery.append(USER_JOIN_SUPPORT_CONDITION);
        return entityManager.createQuery(customQuery.toString(), ClassroomResponseDto.class).getResultList();
    }

    @Override
    public ClassroomResponseDto getClassroomById(Long classId) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_CLASSROOM_RESPONSE_DTO);
        customQuery.append(CLASS_RES_DTO_FIELD);
        customQuery.append(" FROM Classroom c");
        customQuery.append(USER_JOIN_TRAINER_CONDITION);
        customQuery.append(USER_JOIN_SUPPORT_CONDITION);
        customQuery.append(" WHERE c.classId =:classId");
        return entityManager.createQuery(customQuery.toString(), ClassroomResponseDto.class)
                .setParameter("classId", classId).getSingleResult();
    }
}
