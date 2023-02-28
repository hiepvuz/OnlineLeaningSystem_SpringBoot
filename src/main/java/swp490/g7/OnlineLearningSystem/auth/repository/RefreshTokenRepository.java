package swp490.g7.OnlineLearningSystem.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import swp490.g7.OnlineLearningSystem.auth.domain.RefreshToken;
import swp490.g7.OnlineLearningSystem.entities.user.domain.User;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(User user);
}
