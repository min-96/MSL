package Maswillaeng.MSLback.dto.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomResponseDto {

    private Long chatRoomId;
    private String nickName;
    private String userImage;
    private int unReadMsgCnt;

}
