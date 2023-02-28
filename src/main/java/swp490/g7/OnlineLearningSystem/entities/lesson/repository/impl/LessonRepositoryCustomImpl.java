package swp490.g7.OnlineLearningSystem.entities.lesson.repository.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swp490.g7.OnlineLearningSystem.entities.lesson.domain.dto.LessonFilterDto;
import swp490.g7.OnlineLearningSystem.entities.lesson.repository.LessonRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class LessonRepositoryCustomImpl implements LessonRepositoryCustom {
    private static final Logger logger = LogManager.getLogger(LessonRepositoryCustomImpl.class);

    private static final String PATH_LESSON_FILTER_DTO
            = " swp490.g7.OnlineLearningSystem.entities.lesson.domain.dto.LessonFilterDto";

    private static final String LESSON_FILTER_DTO_FIELD
            = " (l.lessonId, l.subject.subjectId, s.subjectName, l.status, l.moduleId, l.name, l.typeLesson, l.videoUrl," +
            " l.lessonText, l.attachmentUrl, l.displayOrder, l.updatedOrder, ss.subjectSettingTitle, l.duration)";

    private static final String SUBJECT_JOIN_CONDITION
            = " LEFT JOIN Subject s ON l.subject.subjectId = s.subjectId";

    private static final String TEST_JOIN_CONDITION
            = " LEFT JOIN Test t ON l.testId = t.testId";

    private static final String SUBJECT_SETTING_JOIN_CONDITION
            = " LEFT JOIN SubjectSetting ss ON l.moduleId = ss.subjectSettingId";

    private static final String AND_CONDITION = " AND ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<LessonFilterDto> findAll(Long testId, Long subjectId, Long moduleId, Boolean status,
                                         String typeLesson, String name) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_LESSON_FILTER_DTO);
        customQuery.append(LESSON_FILTER_DTO_FIELD);
        customQuery.append(" FROM Lesson l");
        customQuery.append(SUBJECT_JOIN_CONDITION);
        customQuery.append(SUBJECT_SETTING_JOIN_CONDITION);
        customQuery.append(" WHERE l.subject.subjectId =:subjectId");
        if (ObjectUtils.isNotEmpty(testId)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("l.testId =:testId");
        }
        if (ObjectUtils.isNotEmpty(moduleId)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("l.moduleId =:moduleId");
        }
        if (ObjectUtils.isNotEmpty(status)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("l.status =:status");
        }
        if (ObjectUtils.isNotEmpty(typeLesson)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("l.typeLesson LIKE CONCAT('%', :typeLesson, '%') ");
        }
        if (ObjectUtils.isNotEmpty(name)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("l.name LIKE CONCAT('%', :name, '%') ");
        }
        TypedQuery<LessonFilterDto> query = entityManager.createQuery(customQuery.toString(), LessonFilterDto.class);
        if (ObjectUtils.isNotEmpty(subjectId)) {
            query.setParameter("subjectId", subjectId);
        }
        if (ObjectUtils.isNotEmpty(testId)) {
            query.setParameter("testId", testId);
        }
        if (ObjectUtils.isNotEmpty(moduleId)) {
            query.setParameter("moduleId", moduleId);
        }
        if (ObjectUtils.isNotEmpty(status)) {
            query.setParameter("status", status);
        }
        if (ObjectUtils.isNotEmpty(typeLesson)) {
            query.setParameter("typeLesson", typeLesson);
        }
        if (ObjectUtils.isNotEmpty(name)) {
            query.setParameter("name", name);
        }
        return query.getResultList();
    }
}
