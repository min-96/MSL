package Maswillaeng.MSLback.dto.user.reponse;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponseDto {

    private String accessToken;
    private String refreshToken;
    private String nickName;
    private String userImage;
}
