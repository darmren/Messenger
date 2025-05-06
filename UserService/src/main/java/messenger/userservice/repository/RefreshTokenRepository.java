package messenger.userservice.repository;

import messenger.userservice.model.RefreshToken;
import messenger.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
    void deleteByToken(RefreshToken refreshToken);
}
