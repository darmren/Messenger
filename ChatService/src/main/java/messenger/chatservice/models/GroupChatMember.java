package messenger.chatservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import messenger.chatservice.enums.GroupChatMemberAuthority;

import java.io.Serializable;

@Entity
@Table(name = "group_chats_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupChatMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupChatMemberAuthority authority;
}

