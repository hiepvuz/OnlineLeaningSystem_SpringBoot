package swp490.g7.OnlineLearningSystem.entities.question.repository.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swp490.g7.OnlineLearningSystem.entities.question.domain.Question;
import swp490.g7.OnlineLearningSystem.entities.question.domain.response.QuestionResponseDto;
import swp490.g7.OnlineLearningSystem.entities.question.repository.QuestionRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class QuestionRepositoryCustomImpl implements QuestionRepositoryCustom {
    private static final Logger logger = LogManager.getLogger(QuestionRepositoryCustomImpl.class);

    private static final String PATH_QUESTION_RESPONSE_DTO
            = " swp490.g7.OnlineLearningSystem.entities.question.domain.response.QuestionResponseDto";

    private static final String QUESTION_RES_DTO_FIELD
            = " (q.questionId, q.body, q.testId, t.testType, s.subjectId, s.subjectName, q.imageUrl, q.explanation," +
            " q.source, q.page, q.lessonId, l.name)";

    private static final String QUESTION_RES_DTO_FIELD_WITHOUT_TEST
            = " (q.questionId, q.body, q.testId, s.subjectId, s.subjectName, q.imageUrl, q.explanation, q.source, q.page, q.lessonId, l.name)";

    private static final String SUBJECT_JOIN_CONDITION
            = " LEFT JOIN Subject s ON q.subject.subjectId = s.subjectId";

    private static final String TEST_JOIN_CONDITION
            = " LEFT JOIN Test t ON q.testId = t.testId";

    private static final String LESSON_JOIN_CONDITION
            = " LEFT JOIN Lesson l ON q.lessonId = l.lessonId";

    private static final String CLASS_LESSON_JOIN_CONDITION
            = " LEFT JOIN ClassLesson cl ON q.classLessonId = cl.classLessonId";

    private static final String AND_CONDITION = " AND ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public QuestionResponseDto getQuestionById(Long questionId) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_QUESTION_RESPONSE_DTO);
        customQuery.append(QUESTION_RES_DTO_FIELD);
        customQuery.append(" FROM Question q");
        customQuery.append(TEST_JOIN_CONDITION);
        customQuery.append(" WHERE q.questionId =:questionId");
        return entityManager.createQuery(customQuery.toString(), QuestionResponseDto.class)
                .setParameter("questionId", questionId).getSingleResult();
    }

    @Override
    public List<QuestionResponseDto> filter(Long subjectId, Long testId, Long lessonId, String body) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new ");
        customQuery.append(PATH_QUESTION_RESPONSE_DTO);
        customQuery.append(QUESTION_RES_DTO_FIELD_WITHOUT_TEST);
        customQuery.append(" FROM Question q");
        customQuery.append(SUBJECT_JOIN_CONDITION);
        customQuery.append(LESSON_JOIN_CONDITION);
        customQuery.append(" WHERE q.subject.subjectId = " + subjectId);
        if (ObjectUtils.isNotEmpty(testId)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("q.testId =:testId");
        } else {
            customQuery.append(AND_CONDITION);
            customQuery.append("q.testId IS NULL");
        }
        if (ObjectUtils.isNotEmpty(lessonId)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("q.lessonId =:lessonId");
        }
        if (ObjectUtils.isNotEmpty(body)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("q.body LIKE CONCAT('%', :body, '%')");
        }
        TypedQuery<QuestionResponseDto> query = entityManager.createQuery(customQuery.toString(), QuestionResponseDto.class);
        if (ObjectUtils.isNotEmpty(testId)) {
            query.setParameter("testId", testId);
        }
        if (ObjectUtils.isNotEmpty(body)) {
            query.setParameter("body", body);
        }
        if (ObjectUtils.isNotEmpty(lessonId)) {
            query.setParameter("lessonId", lessonId);
        }
        return query.getResultList();
    }

    @Override
    public List<Question> findByContentGroupNotIsTestAndQuizLesson(Long contentGroupId) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append(" FROM Question q");
        customQuery.append(LESSON_JOIN_CONDITION);
        customQuery.append(" WHERE q.testId IS NULL");
        customQuery.append(" AND q.contentGroups.groupId = " + contentGroupId);
        customQuery.append(" AND cl.typeLesson <> 'QUIZ_LESSON'");
        return entityManager.createQuery(customQuery.toString(), Question.class).getResultList();
    }
}
