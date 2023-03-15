package Maswillaeng.MSLback.dto.common;

import lombok.Getter;

@Getter
public class ChatListDto {
    private Long roomId;
 //   private String sender;

    private String recipient;

    private Long alarmCount;

    public ChatListDto(Long roomIdString ,String recipient, Long alarmCount) {
        this.roomId = roomId;
 //       this.sender = sender;
        this.recipient = recipient;
        this.alarmCount = alarmCount;
    }
}
