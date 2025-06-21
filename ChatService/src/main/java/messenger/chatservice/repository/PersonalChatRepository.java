package messenger.chatservice.repository;

import messenger.chatservice.models.PersonalChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonalChatRepository extends JpaRepository<PersonalChat, Long> {
    Optional<PersonalChat> findByMember1AndMember2(Long member1, Long member2);
    Optional<PersonalChat> findByMember2AndMember1(Long member2, Long member1);

    default Optional<PersonalChat> findBetweenUsers(Long user1, Long user2) {
        return findByMember1AndMember2(user1, user2)
                .or(() -> findByMember2AndMember1(user1, user2));
    }
}
