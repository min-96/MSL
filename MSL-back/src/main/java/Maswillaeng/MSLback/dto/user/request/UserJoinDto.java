package Maswillaeng.MSLback.dto.user.request;

import Maswillaeng.MSLback.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserJoinDto {

    private String email;

    private String password;

//    private String phoneNumber;

    private String nickName;

    private String userImage;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
//                .phoneNumber(phoneNumber)
                .nickName(nickName)
                .userImage(userImage)
                .build();
    }
}
