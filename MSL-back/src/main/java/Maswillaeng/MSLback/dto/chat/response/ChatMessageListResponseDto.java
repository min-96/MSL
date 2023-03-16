package Maswillaeng.MSLback.dto.chat.response;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.dto.chat.request.ChatMessageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ChatMessageListResponseDto {

    private Long partnerId;
    private String nickName;
    private String userImage;
    private List<ChatMessageDto> chatMessageList;

    public ChatMessageListResponseDto(User user, List<ChatMessageDto> chatMessageDtoList) {
        this.partnerId = user.getId();
        this.nickName = user.getNickName();
        this.userImage = user.getUserImage();
        this.chatMessageList = chatMessageDtoList;
    }
}
