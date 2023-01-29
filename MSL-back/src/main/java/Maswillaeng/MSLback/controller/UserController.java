package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.user.reponse.LoginResponseDto;
import Maswillaeng.MSLback.dto.user.request.LoginRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserJoinDto;
import Maswillaeng.MSLback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    // 프론트에서 어디까지 검증을 해주는가?
    @PostMapping("/sign")
    public ResponseEntity<Object> join(@RequestBody UserJoinDto userJoinDto) {
        userService.join(userJoinDto.toEntity());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto dto = userService.login(requestDto);
        ResponseCookie accessTokenCookie = ResponseCookie
                .from("ACCESS_TOKEN", dto.getAccessToken())
                .httpOnly(true)
                .secure(false) // 당장은 개발중이니 false
                .path("/")
                .build();
        ResponseCookie refreshTokenCookie = ResponseCookie
                .from("REFRESH_TOKEN", dto.getRefreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .build();
        HashMap<String, String> user = new HashMap<>();
        user.put("nickName", dto.getNickName());
        user.put("userImage", dto.getUserImage());
        return ResponseEntity.ok().
                header(HttpHeaders.SET_COOKIE,
                        accessTokenCookie + "; " + refreshTokenCookie)
                .body(user);
    }
}
