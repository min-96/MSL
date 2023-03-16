package Maswillaeng.MSLback.dto.chat.response;

import Maswillaeng.MSLback.domain.entity.Chat;
import Maswillaeng.MSLback.domain.enums.MessageEnum;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


@Getter
public class ChatResponseDto {
    private Long senderId;
    private Long recipientId;
    private String content;
    private Long roomId;
    private LocalDateTime createdAt;
    private Boolean state;
    private MessageEnum type;


    public ChatResponseDto(Chat chat) {
        this.senderId = chat.getSenderId();
        this.recipientId = chat.getRecipientId();
        this.content = chat.getContent();
        this.roomId = chat.getChatRoom().getId();
        this.createdAt = chat.getCreatedAt();
        this.state = chat.isState();
        this.type = MessageEnum.MESSAGE;
    }

}
