package swp490.g7.OnlineLearningSystem.entities.test.repository.impl;

import org.apache.commons.lang3.ObjectUtils;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.TestFilterDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.ContentGroupTotal;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.PrePareLessonConfigDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.QuestionContentQueryDto;
import swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.QuizLessonTotal;
import swp490.g7.OnlineLearningSystem.entities.test.repository.TestRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class TestRepositoryCustomImpl implements TestRepositoryCustom {

    private static final String PATH_CONTENT_TEST_RESPONSE_DTO
            = " swp490.g7.OnlineLearningSystem.entities.test.domain.dto.TestFilterDto";

    private static final String TEST_RES_DTO_FIELD
            = " (t.testId, t.testType, t.name, t.status, t.duration, t.passRate, t.subject.subjectId, s.subjectCode, t.sourceId)";

    private static final String JOIN_SUBJECT_CONDITION
            = " LEFT JOIN Subject s on t.subject.subjectId = s.subjectId";

    private static final String PATH_QUESTION_CONTENT_QUERY_DTO
            = " swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.QuestionContentQueryDto";

    private static final String PATH_QUESTION_LESSON_QUERY_DTO
            = " swp490.g7.OnlineLearningSystem.entities.test.domain.dto.handle.random.QuestionLessonQueryDto";


    @PersistenceContext
    private EntityManager entityManager;

    private static final String AND_CONDITION = " AND ";

    @Override
    public List<TestFilterDto> filter(Long subjectId, Integer testType, String name, Boolean status) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_CONTENT_TEST_RESPONSE_DTO);
        customQuery.append(TEST_RES_DTO_FIELD);
        customQuery.append(" FROM Test t");
        customQuery.append(JOIN_SUBJECT_CONDITION);
        customQuery.append(" WHERE t.subject.subjectId = " + subjectId);

        if (ObjectUtils.isNotEmpty(testType)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("t.testType =:testType");
        } else {
            customQuery.append(AND_CONDITION);
            customQuery.append("t.testType != 5");
        }
        if (ObjectUtils.isNotEmpty(name)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("t.name LIKE CONCAT('%', :name, '%') ");
        }
        if (ObjectUtils.isNotEmpty(status)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("t.status =:status");
        }
        TypedQuery<TestFilterDto> query = entityManager.createQuery(customQuery.toString(), TestFilterDto.class);

        if (ObjectUtils.isNotEmpty(testType)) {
            query.setParameter("testType", testType);
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
    public List<TestFilterDto> getAllBySubject(Long id) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_CONTENT_TEST_RESPONSE_DTO);
        customQuery.append(TEST_RES_DTO_FIELD);
        customQuery.append(" FROM Test t");
        customQuery.append(JOIN_SUBJECT_CONDITION);
        customQuery.append(" WHERE s.subjectId = :id");
        return entityManager.createQuery(customQuery.toString(), TestFilterDto.class)
                .setParameter("id", id).getResultList();
    }

    @Override
    public List<Long> getListQuestionLesson(PrePareLessonConfigDto p) {
        StringBuilder customQuery = new StringBuilder();
        for (QuizLessonTotal q : p.getLessonTotals()) {
            customQuery.append("( select q.question_id from question q join lesson l on q.lesson_id = l.lesson_id where l.lesson_id = ");
            customQuery.append(q.getLessonId());
            customQuery.append(" ORDER BY RAND() LIMIT ");
            customQuery.append(q.getTotal());
            customQuery.append(" ) UNION ");
        }
        customQuery.replace(customQuery.length() - " UNION ".length(), customQuery.length(), "");
        List<Long> listQuestionId = (List<Long>) entityManager.createNativeQuery(customQuery.toString()).getResultList();
        return listQuestionId;
    }

    @Override
    public List<QuestionContentQueryDto> getListQuestionWithContentGroup(PrePareLessonConfigDto p) {
        StringBuilder customQuery = new StringBuilder();
        for (ContentGroupTotal q : p.getContentGroupTotals()) {
            customQuery.append(" (SELECT q.question_id as questionId, qg.group_id as groupId");
            customQuery.append(" FROM question q");
            customQuery.append(" JOIN question_group qg ON q.question_id = qg.question_id");
            customQuery.append(" WHERE qg.group_id = ");
            customQuery.append(q.getGroupId());
            customQuery.append(" ORDER BY RAND() LIMIT ");
            customQuery.append(q.getTotal());
            customQuery.append(" ) UNION ALL ");
        }
        customQuery.replace(customQuery.length() - " UNION ALL".length(), customQuery.length(), "");
        List<Object[]> result = entityManager.createNativeQuery(customQuery.toString()).getResultList();
        List<QuestionContentQueryDto> questionContentQuery = new ArrayList<>();
        result.forEach(r -> {
            QuestionContentQueryDto questionContentQueryDto = new QuestionContentQueryDto();
            questionContentQueryDto.setQuestionId(new Long(String.valueOf(r[0])));
            questionContentQueryDto.setGroupId(new Long(String.valueOf(r[1])));
            questionContentQuery.add(questionContentQueryDto);
        });
        return questionContentQuery;
    }

    @Override
    public List<TestFilterDto> getAllTestForQuizLesson(Long subjectId) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_CONTENT_TEST_RESPONSE_DTO);
        customQuery.append(TEST_RES_DTO_FIELD);
        customQuery.append(" FROM Test t");
        customQuery.append(JOIN_SUBJECT_CONDITION);
        customQuery.append(" WHERE t.subject.subjectId = " + subjectId);
        customQuery.append(" AND t.testType = 4 AND t.sourceId IS NULL");
        TypedQuery<TestFilterDto> query = entityManager.createQuery(customQuery.toString(), TestFilterDto.class);
        return query.getResultList();
    }
}
