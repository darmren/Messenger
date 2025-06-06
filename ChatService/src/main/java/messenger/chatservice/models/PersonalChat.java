package messenger.chatservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "personal_chats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalChat {
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @OneToOne
    @JoinColumn(name = "chat_id")
    @MapsId
    private Chat chat;

    @Column(name = "member1_id")
    private Long member1;

    @Column(name = "member2_id")
    private Long member2;
}

