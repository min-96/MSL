package Maswillaeng.MSLback.dto.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPwdResetRequestDto {
    private String email;
    private String password;
    private String token;
}
