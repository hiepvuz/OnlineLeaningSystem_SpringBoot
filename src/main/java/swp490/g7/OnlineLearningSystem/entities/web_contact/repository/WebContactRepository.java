package swp490.g7.OnlineLearningSystem.entities.web_contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swp490.g7.OnlineLearningSystem.entities.web_contact.domain.WebContact;

import java.util.List;

public interface WebContactRepository extends JpaRepository<WebContact, Long> {

    List<WebContact> findByUserIdOrUserIdIsNull(Long userId);
}
