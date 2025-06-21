package messenger.chatservice.repository;

import messenger.chatservice.models.Chat;
import messenger.chatservice.models.PersonalChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
