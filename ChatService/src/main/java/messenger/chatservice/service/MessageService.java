package messenger.chatservice.service;

import lombok.RequiredArgsConstructor;
import messenger.chatservice.client.UserClient;
import messenger.chatservice.dto.ChatMessageDto;
import messenger.chatservice.enums.ChatType;
import messenger.chatservice.exception.UserIdNotFoundException;
import messenger.chatservice.models.Chat;
import messenger.chatservice.models.Message;
import messenger.chatservice.models.PersonalChat;
import messenger.chatservice.repository.ChatRepository;
import messenger.chatservice.repository.MessageRepository;
import messenger.chatservice.repository.PersonalChatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final PersonalChatRepository personalChatRepository;
    private final UserClient userClient;
    private final ChatService chatService;

    public Message sendPersonalMessage(ChatMessageDto dto) {
        if (userClient.getUserById(dto.getSenderId()) == null){
            throw new UserIdNotFoundException(dto.getSenderId().toString());
        }

        if (userClient.getUserById(dto.getReceiverId()) == null){
            throw new UserIdNotFoundException(dto.getReceiverId().toString());
        }

//        PersonalChat personalChat = personalChatRepository.findById(dto.getChatId()).orElse(
//                chatService.createPersonalChat(dto.getSenderId(), dto.getReceiverId()));

        PersonalChat personalChat = personalChatRepository
                .findBetweenUsers(dto.getSenderId(), dto.getReceiverId())
                .orElseGet(()->chatService.createPersonalChat(dto.getSenderId(), dto.getReceiverId()));

        Message message = Message.builder()
                .chat(personalChat.getChat())
                .senderId(dto.getSenderId())
                .text(dto.getText())
                .sendAt(Instant.now())
                .build();

        return messageRepository.save(message);
    }



}

