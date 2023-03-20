package Maswillaeng.MSLback.dto.user.reponse;

import Maswillaeng.MSLback.domain.entity.ChatRoom;
import Maswillaeng.MSLback.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UserInfoResponseDto {

    private String email;

    private String nickName;

    private String userImage;

    private String introduction;

    private int postCnt;

    private boolean followState;

    private int followerCnt;

    private int followingCnt;

    private boolean hasChatted;


    public UserInfoResponseDto(int followingCnt, int followerCnt, User user, boolean isFollowed, ChatRoom chatRoom) {
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.userImage = user.getUserImage();
        this.introduction = user.getIntroduction();
        this.postCnt = user.getPostList().size();
        this.followState = isFollowed;
        this.followerCnt = followerCnt;
        this.followingCnt = followingCnt;
        this.hasChatted = !(chatRoom == null);
    }
    public UserInfoResponseDto(int followingCnt, int followerCnt, User user) {
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.userImage = user.getUserImage();
        this.introduction = user.getIntroduction();
        this.postCnt = user.getPostList().size();
        this.followState = false;
        this.followerCnt = followerCnt;
        this.followingCnt = followingCnt;
        this.hasChatted =false;
    }
}
