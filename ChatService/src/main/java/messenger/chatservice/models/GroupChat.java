package messenger.chatservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_chats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupChat {
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @OneToOne
    @JoinColumn(name = "chat_id")
    @MapsId
    private Chat chat;

    @Column(name = "owner_id", nullable = false)
    private Long owner_id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "members_count", nullable = false)
    private Integer membersCount;
}

