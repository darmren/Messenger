package messenger.chatservice.mapper;

import lombok.AllArgsConstructor;
import messenger.chatservice.client.UserClient;
import messenger.chatservice.dto.MessageResponseDto;
import messenger.chatservice.models.Message;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Component
public class MessageMapper {

    private final UserClient userClient;

    public MessageResponseDto toResponseDto(Message message) {
        String senderName = userClient.getUserById(message.getSenderId()).getUsername();

        return MessageResponseDto.builder()
                .senderName(senderName)
                //.sendAt(LocalDateTime.ofInstant(message.getSendAt(), ZoneId.systemDefault()))
                .sendAt(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm").format(message.getSendAt().atOffset(ZoneOffset.UTC)))
                .text(message.getText())
                .build();
    }

}

