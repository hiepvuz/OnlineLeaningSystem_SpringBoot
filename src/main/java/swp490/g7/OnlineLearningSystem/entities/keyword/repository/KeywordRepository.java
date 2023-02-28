package swp490.g7.OnlineLearningSystem.entities.keyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.entities.keyword.domain.Keyword;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long>, KeywordRepositoryCustom {

    @Query(value = "select * from keyword k left join keyword_group kg on k.keyword_id = kg.keyword_id " +
            "            where kg.group_id = ?1 ORDER BY RAND() limit ?2 ", nativeQuery = true)
    List<Keyword> findKeywordRandomByGroupIdAndQuantity(Long groupId, Integer quantity);

    Boolean existsByKeyword(String keyword);

    @Query(value = "SELECT COUNT(k.keywordId) FROM Keyword k LEFT JOIN KeywordGroup kg on k.keywordId = kg.keyword.keywordId " +
            "WHERE kg.contentGroup.groupId = ?1 AND kg.contentGroup.groupId IS NOT NULL")
    Integer countKeywordByContentGroupId(Long contentGroupId);
}
