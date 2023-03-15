package Maswillaeng.MSLback.dto.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomResponseDto {

    private Long chatRoomId;
    private Long partnerId;
    private String nickName;
    private String userImage;
    private Long unReadMsgCnt;
    private String lastMessage;
    private LocalDateTime lastMessageCreatedAt;

}
