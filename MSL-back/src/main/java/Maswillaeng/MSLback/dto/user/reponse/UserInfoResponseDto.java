package Maswillaeng.MSLback.dto.user.reponse;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO : followState 추가 -> 미뇽님이
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserInfoResponseDto {

    private String email;

    private String nickName;

    private String userImage;

    private String introduction;

    public static UserInfoResponseDto of(User user) {
        return new UserInfoResponseDto(
                user.getEmail(),
                user.getNickName(),
                user.getUserImage(),
                user.getIntroduction());
    }
}
