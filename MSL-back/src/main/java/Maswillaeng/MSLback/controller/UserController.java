package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.dto.user.reponse.LoginResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.UserInfoResponseDto;
import Maswillaeng.MSLback.dto.user.request.LoginRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserJoinDto;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import Maswillaeng.MSLback.service.UserService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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

    // 로직이 컨트롤러에 너무 많이 노출되어있다
    // 이런 방식이 옳은가?
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto dto = userService.login(requestDto);

        ResponseCookie accessToken = ResponseCookie.from("ACCESS_TOKEN", dto.getAccessToken())
                .httpOnly(true).build();
//
//        Cookie refreshCookie = new Cookie("REFRESH_TOKEN", dto.getRefreshToken());
//        refreshCookie.setHttpOnly(true);
//        response.addCookie(refreshCookie);
        HashMap<String, String> user = new HashMap<>();
        user.put("nickName", dto.getNickName());
        user.put("userImage", dto.getUserImage());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessToken.toString()).body(user);
    }

    @AuthCheck
    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo() {
        User user = UserContext.currentMember.get();
        return ResponseEntity.ok().body(UserInfoResponseDto.of(user));
    }

    @AuthCheck
    @PutMapping("/user")
    public ResponseEntity<Object> updateUserInfo(
                         @RequestBody @Valid UserUpdateRequestDto requestDto) {
        User user = UserContext.currentMember.get();
        userService.updateUser(user, requestDto);
        return ResponseEntity.ok().build();
    }

    @AuthCheck
    @DeleteMapping("/user")
    public ResponseEntity<Object> userWithDraw() {
        User user = UserContext.currentMember.get();
        userService.userWithdraw(user);
        return ResponseEntity.ok().build();
    }
}
