package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.reponse.LoginResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.UserLoginResponseDto;
import Maswillaeng.MSLback.dto.user.request.LoginRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserJoinDto;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import Maswillaeng.MSLback.service.UserService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/duplicate-email")
    public ResponseEntity<Object> duplicateEmail(@RequestParam String email) {
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @GetMapping("/duplicate-nickname")
    public ResponseEntity<Object> duplicateNickname(@RequestParam String nickname) {
        if (userRepository.existsByNickName(nickname)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    // 프론트에서 어디까지 검증을 해주는가?
    @PostMapping("/sign")
    public ResponseEntity<Object> join(@RequestBody UserJoinDto userJoinDto) {
        User user = userJoinDto.toEntity();
        if (userService.joinDuplicate(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            userService.join(user);
            return ResponseEntity.ok().build();
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request, HttpServletResponse response) throws Exception {
        LoginResponseDto dto = userService.login(request);

        ResponseCookie AccessToken = ResponseCookie.from("ACCESS_TOKEN", dto.getTokenResponseDto().getACCESS_TOKEN())
                .path("/")
                .httpOnly(true)
                .maxAge(JwtTokenProvider.REFRESH_TOKEN_VALID_TIME)
                .build();

        ResponseCookie RefreshToken = ResponseCookie.from("REFRESH_TOKEN", dto.getTokenResponseDto().getREFRESH_TOKEN())
                .path("/updateToken")
                .maxAge(JwtTokenProvider.REFRESH_TOKEN_VALID_TIME)
                .httpOnly(true)
                .build();

        response.addHeader("Set-Cookie", AccessToken.toString());
        response.addHeader("Set-Cookie", RefreshToken.toString());

        return ResponseEntity.ok().body(
                new UserLoginResponseDto(dto.getNickName(), dto.getUserImage()));
    }



    @AuthCheck(role = AuthCheck.Role.USER)
    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo() {
        return ResponseEntity.ok().body(
                userService.getUser(UserContext.userId.get()));
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @GetMapping("/updateToken")
    public ResponseEntity<Object> updateAccessToken(@CookieValue("ACCESS_TOKEN") String accessToken, @CookieValue("REFRESH_TOKEN") String refreshToken) throws Exception {

        return ResponseEntity.ok().body(userService.updateAccessToken(accessToken, refreshToken));
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @PutMapping("/user")
    public ResponseEntity<Object> updateUserInfo(
            @RequestBody @Valid UserUpdateRequestDto requestDto) {
        if (requestDto.getPassword() == null && requestDto.getNickName() == null) {
            return ResponseEntity.badRequest().build();
        }
        userService.updateUser(UserContext.userId.get(), requestDto);
        return ResponseEntity.ok().build();
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @DeleteMapping("/user")
    public ResponseEntity<Object> userWithDraw() {
        userService.userWithdraw(UserContext.userId.get());
        return ResponseEntity.ok().build();
    }
}
