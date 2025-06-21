package messenger.chatservice.repository;

import messenger.chatservice.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE " +
            "(m.senderId = :user1Id) OR " +
            "(m.senderId = :user2Id) " +
            "ORDER BY m.sendAt ASC")
    List<Message> findChatBetween(@Param("user1Id") Long user1, @Param("user2Id") Long user2Id);
}