package Maswillaeng.MSLback.dto.common;

import Maswillaeng.MSLback.domain.entity.ChatRoom;
import lombok.Getter;

@Getter
public class CreateRoomResponseDto {

    private Long roomId;

    private String owner;

    private String invited;

    public CreateRoomResponseDto(ChatRoom chatRoom) {
        this.roomId = chatRoom.getId();
        this.owner = chatRoom.getOwner().getNickName();
        this.invited = chatRoom.getInvited().getNickName();
    }
}
