package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.LoginResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.TokenResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.UserLoginResponseDto;
import Maswillaeng.MSLback.dto.user.request.LoginRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserJoinDto;
import Maswillaeng.MSLback.service.AuthService;
import Maswillaeng.MSLback.service.MailService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.UserContext;
import Maswillaeng.MSLback.utils.auth.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static Maswillaeng.MSLback.common.message.SuccessMessage.*;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final MailService mailService;

    @PostMapping("/api/duplicate-email")
    public ResponseEntity<Object> duplicateEmail(@RequestBody Map<String, String> email) {
        if (userRepository.existsByEmail(email.get("email"))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok().body(ResponseDto.of(
                    SUCCESS_CHECK_DUPLICATE_EMAIL));
        }
    }

    @PostMapping("/api/duplicate-nickname")
    public ResponseEntity<Object> duplicateNickname(@RequestBody Map<String, String> nickName) {
        if (userRepository.existsByNickName(nickName.get("nickName"))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok().body(ResponseDto.of(
                    SUCCESS_CHECK_DUPLICATE_NICKNAME));
        }
    }

    @PostMapping("/api/sign")
    public ResponseEntity<Object> join(@RequestBody UserJoinDto userJoinDto) throws Exception {
        User user = userJoinDto.toEntity();
        if (authService.joinDuplicate(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            authService.join(user);
            return ResponseEntity.ok().body(ResponseDto.of(
                    SUCCESS_SIGNED_UP));
        }
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) throws Exception {

        LoginResponseDto dto = authService.login(request);
        ResponseCookie AccessToken = authService.getAccessTokenCookie(
                dto.getTokenResponseDto().getACCESS_TOKEN());
        ResponseCookie RefreshToken = authService.getRefreshTokenCookie(
                dto.getTokenResponseDto().getREFRESH_TOKEN());

        return ResponseEntity.ok()
                .header("Set-Cookie", AccessToken.toString())
                .header("Set-Cookie", RefreshToken.toString())
                .body(ResponseDto.of(
                        SUCCESS_LOGGED_IN,
                        new UserLoginResponseDto(dto.getNickName(), dto.getUserImage())));
    }


    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/api/logout")
    public ResponseEntity<Object> logout() {

        Long userId = UserContext.userData.get().getUserId();
        authService.removeRefreshToken(userId);

        return ResponseEntity.ok()
                .header("Set-Cookie", "ACCESS_TOKEN=; path=/; max-age=0; expires=0;")
                .header("Set-Cookie", "REFRESH_TOKEN=; path=/updateToken; max-age=0; expires=0;")
                .body(ResponseDto.of(
                        SUCCESS_LOGGED_OUT));
    }

    @ValidToken
    @GetMapping("/api/update-token")
    public ResponseEntity<Object> updateAccessToken(@CookieValue("REFRESH_TOKEN") String refreshToken) throws Exception {

        TokenResponseDto token = authService.updateAccessToken(refreshToken);
        ResponseCookie AccessToken = authService.getAccessTokenCookie(
                token.getACCESS_TOKEN());

        return ResponseEntity.ok()
                .header("Set-Cookie", AccessToken.toString())
                .body(ResponseDto.of(
                        SUCCESS_REISSUE_TOKEN));
    }

    @PostMapping("/api/pwd-reset-mail")
    public ResponseEntity<?> sendPwdResetMail(@RequestBody Map<String, String> email) {
        mailService.sendPasswordResetMail(email.get("email"));
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_SEND_PASSWORD_RESET_MAIL));
    }
}
