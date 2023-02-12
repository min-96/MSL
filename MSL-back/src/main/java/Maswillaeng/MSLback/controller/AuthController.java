package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.ImportResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.LoginResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.UserLoginResponseDto;
import Maswillaeng.MSLback.dto.user.request.LoginRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserJoinDto;
import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import Maswillaeng.MSLback.service.AuthService;
import Maswillaeng.MSLback.service.ExternalHttpService;
import Maswillaeng.MSLback.service.UserService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final ExternalHttpService externalHttpService;

    @PostMapping("/duplicate-email")
    public ResponseEntity<Object> duplicateEmail(@RequestBody String email) {
        if (userRepository.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/duplicate-nickname")
    public ResponseEntity<Object> duplicateNickname(@RequestBody String nickname) {
        if (userRepository.existsByNickName(nickname)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/sign")
    public ResponseEntity<Object> join(@RequestBody UserJoinDto userJoinDto) throws Exception {
        User user = userJoinDto.toEntity();
        System.out.println("user = " + user);
        System.out.println("userEmail = " + user.getEmail());
        if (authService.joinDuplicate(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            authService.join(user);
            return ResponseEntity.ok().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request,
                                   HttpServletResponse response) throws Exception {
        LoginResponseDto dto = authService.login(request);

        ResponseCookie AccessToken = ResponseCookie.from(
                "ACCESS_TOKEN", dto.getTokenResponseDto().getACCESS_TOKEN())
                .path("/")
                .httpOnly(true)
                .maxAge(JwtTokenProvider.ACCESS_TOKEN_VALID_TIME)
                .build();

        ResponseCookie RefreshToken = ResponseCookie.from(
                "REFRESH_TOKEN", dto.getTokenResponseDto().getREFRESH_TOKEN())
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
    @DeleteMapping("/logout")
    public ResponseEntity<Object> logout() {
        authService.removeRefreshToken(UserContext.userId.get());
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK,
                "로그아웃 성공")
        );
    }

    //TODO : 토큰을 그냥 바디에 담아준다?
    @AuthCheck(role = AuthCheck.Role.USER)
    @GetMapping("/updateToken")
    public ResponseEntity<Object> updateAccessToken(@CookieValue("ACCESS_TOKEN") String accessToken,
                                                    @CookieValue("REFRESH_TOKEN") String refreshToken) throws Exception {

        return ResponseEntity.ok().body(
                authService.updateAccessToken(accessToken, refreshToken));
    }

    @PostMapping("/certifications")
    public ResponseEntity<Objects> impUid(@RequestBody String uid) throws Exception {

        String access_token = String.valueOf(externalHttpService.importGetToken());
        ResponseEntity<ImportResponseDto> dto = externalHttpService.importGetCertifications(uid, access_token);
        authService.adultIdentify(dto.getBody().getBirth());
//        authService.saveUserBirthAndCI(dto); // TODO : 유저에 어떤 필드 추가할건지 정확히 정한 후 작성

        return ResponseEntity.ok().build();
    }
}
