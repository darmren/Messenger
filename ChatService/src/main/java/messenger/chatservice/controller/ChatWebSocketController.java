package messenger.chatservice.controller;

import lombok.RequiredArgsConstructor;
import messenger.chatservice.dto.ChatMessageDto;
import messenger.chatservice.dto.MessageResponseDto;
import messenger.chatservice.mapper.MessageMapper;
import messenger.chatservice.models.Message;
import messenger.chatservice.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageMapper messageMapper;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessageDto messageDto) {
        Message savedMessage = messageService.sendPersonalMessage(messageDto);
        MessageResponseDto messageResponseDto = messageMapper.toResponseDto(savedMessage);
        messagingTemplate.convertAndSend(
                "/topic/chat/" + messageDto.getChatId(),
                messageResponseDto
        );
    }
}

