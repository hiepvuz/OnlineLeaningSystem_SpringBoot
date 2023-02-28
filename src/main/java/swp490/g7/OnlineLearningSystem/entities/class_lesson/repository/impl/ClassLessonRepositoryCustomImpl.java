package swp490.g7.OnlineLearningSystem.entities.class_lesson.repository.impl;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.dto.ClassLessonFilterDto;
import swp490.g7.OnlineLearningSystem.entities.class_lesson.repository.ClassLessonRepositoryCustom;
import swp490.g7.OnlineLearningSystem.entities.lesson.repository.impl.LessonRepositoryCustomImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class ClassLessonRepositoryCustomImpl implements ClassLessonRepositoryCustom {
    private static final Logger logger = LogManager.getLogger(LessonRepositoryCustomImpl.class);

    private static final String PATH_LESSON_FILTER_DTO
            = " swp490.g7.OnlineLearningSystem.entities.class_lesson.domain.dto.ClassLessonFilterDto";

    private static final String LESSON_FILTER_DTO_FIELD
            = " (cl.classLessonId, cl.subject.subjectId, s.subjectName, c.classId, c.classCode, cl.status, cl.moduleId," +
            " cl.name, cl.typeLesson, cl.videoUrl, cl.lessonText, cl.attachmentUrl, cl.displayOrder, cl.updatedOrder," +
            " cs.classSettingTitle, cl.duration, cl.testId)";

    private static final String SUBJECT_JOIN_CONDITION
            = " LEFT JOIN Subject s ON cl.subject.subjectId = s.subjectId";

    private static final String CLASS_JOIN_CONDITION
            = " LEFT JOIN Classroom c ON cl.classroom.classId = c.classId";

    private static final String CLASS_SETTING_JOIN_CONDITION
            = " LEFT JOIN ClassSetting cs ON cl.moduleId = cs.classSettingId";

    private static final String AND_CONDITION = " AND ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ClassLessonFilterDto> findAll(Long subjectId, Long classId, Long moduleId, Boolean status,
                                              String typeLesson, String name) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_LESSON_FILTER_DTO);
        customQuery.append(LESSON_FILTER_DTO_FIELD);
        customQuery.append(" FROM ClassLesson cl");
        customQuery.append(SUBJECT_JOIN_CONDITION);
        customQuery.append(CLASS_JOIN_CONDITION);
        customQuery.append(CLASS_SETTING_JOIN_CONDITION);
        customQuery.append(" WHERE cl.subject.subjectId =:subjectId");
        if (ObjectUtils.isNotEmpty(classId)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("cl.classroom.classId =:classId");
        }
        if (ObjectUtils.isNotEmpty(moduleId)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("cl.moduleId =:moduleId");
        }
        if (ObjectUtils.isNotEmpty(status)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("cl.status =:status");
        }
        if (ObjectUtils.isNotEmpty(typeLesson)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("cl.typeLesson LIKE CONCAT('%', :typeLesson, '%') ");
        }
        if (ObjectUtils.isNotEmpty(name)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("cl.name LIKE CONCAT('%', :name, '%') ");
        }
        TypedQuery<ClassLessonFilterDto> query = entityManager.createQuery(customQuery.toString(), ClassLessonFilterDto.class);
        if (ObjectUtils.isNotEmpty(subjectId)) {
            query.setParameter("subjectId", subjectId);
        }
        if (ObjectUtils.isNotEmpty(classId)) {
            query.setParameter("classId", classId);
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
