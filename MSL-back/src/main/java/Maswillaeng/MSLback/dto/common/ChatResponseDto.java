package Maswillaeng.MSLback.dto.common;

import Maswillaeng.MSLback.domain.entity.Chat;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;


@Getter
public class ChatResponseDto {

    private String sender;
    private String recipient;
    private String content;
    private Long roomId;
    private LocalDateTime createdAt;
    private Boolean state;

    public ChatResponseDto(Chat chat) {
        this.sender = chat.getSender();
        this.recipient = chat.getRecipient();
        this.content = chat.getContent();
        this.roomId = chat.getChatRoom().getId();
        this.createdAt = chat.getCreatedAt();
        this.state = chat.isState();
    }

}
