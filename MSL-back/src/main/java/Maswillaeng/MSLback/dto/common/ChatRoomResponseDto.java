package Maswillaeng.MSLback.dto.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomResponseDto {

    private Long chatRoomId;
    private String nickName;
    private String userImage;
    private Long unReadMsgCnt;


}
