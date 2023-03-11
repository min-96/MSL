package Maswillaeng.MSLback.dto.common;

import lombok.Getter;

@Getter
public class ChatListDto {
    private Long roomId;
    private String sender;

    private String recipient;

    private int alarmCount;

    public ChatListDto(Long roomId, String sender, String recipient, int alarmCount) {
        this.roomId = roomId;
        this.sender = sender;
        this.recipient = recipient;
        this.alarmCount = alarmCount;
    }
}
