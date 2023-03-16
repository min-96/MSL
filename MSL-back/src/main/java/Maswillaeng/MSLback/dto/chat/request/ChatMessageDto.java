package Maswillaeng.MSLback.dto.chat.request;
import Maswillaeng.MSLback.domain.entity.Chat;
import Maswillaeng.MSLback.domain.enums.MessageEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ChatMessageDto {
    private String chatId;
    private Long chatRoomId;
    private Long senderId;
    private Long recipientId;
    private String content;
    private LocalDateTime createdAt;

    public ChatMessageDto(Chat chat) {
        this.chatId = chat.getId();
        this.senderId = chat.getSenderId();
        this.recipientId = chat.getRecipientId();
        this.content = chat.getContent();
        this.createdAt = chat.getCreatedAt();
    }
}
