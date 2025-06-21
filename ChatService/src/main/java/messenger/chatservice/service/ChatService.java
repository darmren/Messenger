package messenger.chatservice.service;

import lombok.AllArgsConstructor;
import messenger.chatservice.enums.ChatType;
import messenger.chatservice.models.Chat;
import messenger.chatservice.models.PersonalChat;
import messenger.chatservice.repository.ChatRepository;
import messenger.chatservice.repository.PersonalChatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final PersonalChatRepository personalChatRepository;

    @Transactional
    public PersonalChat createPersonalChat(Long sender, Long receiver) {
        Chat chat = chatRepository.save(Chat.builder()
                .type(ChatType.PERSONAL)
                .build());

        // chat теперь managed entity

        PersonalChat personalChat = new PersonalChat();
        personalChat.setChat(chat); // важно — chat должен быть managed
        personalChat.setMember1(sender);
        personalChat.setMember2(receiver);

        return personalChatRepository.save(personalChat); // Hibernate корректно обработает
    }
}
