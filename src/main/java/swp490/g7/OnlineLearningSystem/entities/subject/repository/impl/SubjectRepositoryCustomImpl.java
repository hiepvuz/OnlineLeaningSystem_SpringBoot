package swp490.g7.OnlineLearningSystem.entities.subject.repository.impl;

import org.apache.commons.lang3.ObjectUtils;
import swp490.g7.OnlineLearningSystem.entities.subject.domain.response.SubjectResponseDto;
import swp490.g7.OnlineLearningSystem.entities.subject.repository.SubjectRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class SubjectRepositoryCustomImpl implements SubjectRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String PATH_SUBJECT_RESPONSE_DTO
            = " swp490.g7.OnlineLearningSystem.entities.subject.domain.response.SubjectResponseDto";

    private static final String SUBJECT_RES_DTO_FIELD
            = " (s.subjectId, s.subjectCode, s.subjectName, s.categoryId, m.username, s.managerId, s.expertId," +
            " e.username, s.status, s.body)";

    private static final String AND_CONDITION = " AND ";

    @Override
    public List<SubjectResponseDto> getAll() {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_SUBJECT_RESPONSE_DTO);

        customQuery.append(SUBJECT_RES_DTO_FIELD);
        customQuery.append(" FROM Subject s");
        customQuery.append(" left join User m on s.managerId = m.userId");
        customQuery.append(" left join User e on s.expertId = e.userId");
        return entityManager.createQuery(customQuery.toString(), SubjectResponseDto.class).getResultList();
    }

    @Override
    public SubjectResponseDto getSubjectById(Long subjectId) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_SUBJECT_RESPONSE_DTO);
        customQuery.append(SUBJECT_RES_DTO_FIELD);
        customQuery.append(" FROM Subject s");
        customQuery.append(" LEFT JOIN User m on s.managerId = m.userId");
        customQuery.append(" LEFT JOIN User e on s.expertId = e.userId");
        customQuery.append(" WHERE s.subjectId =:subjectId");
        return entityManager.createQuery(customQuery.toString(), SubjectResponseDto.class)
                .setParameter("subjectId", subjectId)
                .getSingleResult();
    }

    @Override
    public List<SubjectResponseDto> filter(String subjectName, String subjectCode, Boolean status) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_SUBJECT_RESPONSE_DTO);

        customQuery.append(SUBJECT_RES_DTO_FIELD);
        customQuery.append(" FROM Subject s");
        customQuery.append(" LEFT JOIN User m on s.managerId = m.userId");
        customQuery.append(" LEFT JOIN User e on s.expertId = e.userId");
        customQuery.append(" WHERE 1 = 1");

        if (ObjectUtils.isNotEmpty(subjectName)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("s.subjectName LIKE CONCAT('%', :subjectName, '%')");
        }
        if (ObjectUtils.isNotEmpty(subjectCode)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("s.subjectCode LIKE CONCAT('%', :subjectCode, '%') or :subjectCode is null");
        }
        if (ObjectUtils.isNotEmpty(status)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("s.status =:status");
        }
        TypedQuery<SubjectResponseDto> query = entityManager.createQuery(customQuery.toString(), SubjectResponseDto.class);

        if (ObjectUtils.isNotEmpty(subjectName)) {
            query.setParameter("subjectName", subjectName);
        }
        if (ObjectUtils.isNotEmpty(subjectCode)) {
            query.setParameter("subjectCode", subjectCode);
        }
        if (ObjectUtils.isNotEmpty(status)) {
            query.setParameter("status", status);
        }
        return query.getResultList();
    }
}
