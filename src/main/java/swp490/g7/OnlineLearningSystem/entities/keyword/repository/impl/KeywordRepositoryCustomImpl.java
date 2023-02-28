package swp490.g7.OnlineLearningSystem.entities.keyword.repository.impl;

import org.apache.commons.lang3.ObjectUtils;
import swp490.g7.OnlineLearningSystem.entities.keyword.domain.response.KeywordResponseDto;
import swp490.g7.OnlineLearningSystem.entities.keyword.repository.KeywordRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class KeywordRepositoryCustomImpl implements KeywordRepositoryCustom {

    private static final String PATH_KEYWORD_RESPONSE_DTO
            = " swp490.g7.OnlineLearningSystem.entities.keyword.domain.response.KeywordResponseDto";

    private static final String KEYWORD_RES_DTO_FIELD
            = " (k.keywordId, k.keyword, k.excerpt, k.body, k.status, k.categoryId, ss.subjectSettingTitle)";

    private static final String JOIN_KEYWORD_GROUP_CONDITION
            = " RIGHT JOIN KeywordGroup kg ON k.keywordId = kg.keyword.keywordId";

    private static final String JOIN_CONTENT_GROUP_CONDITION
            = " LEFT JOIN ContentGroup cg ON kg.contentGroup.groupId = cg.groupId";

    private static final String JOIN_SUBJECT_SETTING_CONDITION
            = " LEFT JOIN SubjectSetting ss ON k.categoryId = ss.subjectSettingId";

    private static final String AND_CONDITION = " AND ";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<KeywordResponseDto> findByGroupId(Long contentGroupId, Long subjectId) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_KEYWORD_RESPONSE_DTO);
        customQuery.append(KEYWORD_RES_DTO_FIELD);
        customQuery.append(" from Keyword k");
        customQuery.append(JOIN_KEYWORD_GROUP_CONDITION);
        customQuery.append(JOIN_SUBJECT_SETTING_CONDITION);
        customQuery.append(" WHERE kg.contentGroup.groupId =:groupId AND ss.subjectId =:subjectId");
        return entityManager.createQuery(customQuery.toString(), KeywordResponseDto.class)
                .setParameter("subjectId", subjectId)
                .setParameter("groupId", contentGroupId)
                .getResultList();
    }

    @Override
    public KeywordResponseDto getKeywordById(Long id) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_KEYWORD_RESPONSE_DTO);
        customQuery.append(KEYWORD_RES_DTO_FIELD);
        customQuery.append(" from Keyword k");
        customQuery.append(JOIN_SUBJECT_SETTING_CONDITION);
        customQuery.append(" where k.keywordId = :id");
        return entityManager.createQuery(customQuery.toString(), KeywordResponseDto.class)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    public List<KeywordResponseDto> filter(String keyword, Boolean status, Long categoryId, Long groupId, Long subjectId) {
        StringBuilder customQuery = new StringBuilder();
        customQuery.append("SELECT new");
        customQuery.append(PATH_KEYWORD_RESPONSE_DTO);
        customQuery.append(KEYWORD_RES_DTO_FIELD);
        customQuery.append(" FROM Keyword k");
        customQuery.append(JOIN_KEYWORD_GROUP_CONDITION);
        customQuery.append(JOIN_SUBJECT_SETTING_CONDITION);
        customQuery.append(" WHERE ss.subjectId =:subjectId");
        if (ObjectUtils.isNotEmpty(groupId)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("kg.contentGroup.groupId =:groupId");
        }
        if (ObjectUtils.isNotEmpty(categoryId)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("k.categoryId =:categoryId");
        }
        if (ObjectUtils.isNotEmpty(keyword)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("k.keyword LIKE CONCAT('%', :keyword, '%') ");
        }
        if (ObjectUtils.isNotEmpty(status)) {
            customQuery.append(AND_CONDITION);
            customQuery.append("k.status =:status ");
        }
        TypedQuery<KeywordResponseDto> query = entityManager.createQuery(customQuery.toString(), KeywordResponseDto.class);
        if (ObjectUtils.isNotEmpty(groupId)) {
            query.setParameter("groupId", groupId);
        }
        if (ObjectUtils.isNotEmpty(categoryId)) {
            query.setParameter("categoryId", categoryId);
        }
        if (ObjectUtils.isNotEmpty(keyword)) {
            query.setParameter("keyword", keyword);
        }
        if (ObjectUtils.isNotEmpty(status)) {
            query.setParameter("status", status);
        }
        if (ObjectUtils.isNotEmpty(groupId)) {
            query.setParameter("groupId", groupId);
        }
        query.setParameter("subjectId", subjectId);
        return query.getResultList();
    }
}
