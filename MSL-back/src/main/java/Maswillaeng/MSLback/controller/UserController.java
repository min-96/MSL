package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.UserApiResponse;
import Maswillaeng.MSLback.dto.user.request.UserPwdResetRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import Maswillaeng.MSLback.service.UserService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.UserContext;
import Maswillaeng.MSLback.utils.auth.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    //////////////////////////////// 종속되어있지 않는 user api
    @ValidToken
    @GetMapping("/api/user")
    public ResponseEntity<?> getUserApi() {
        if (UserContext.userData.get() == null) return ResponseEntity.ok().body(new UserApiResponse(false));

        return ResponseEntity.ok().body(userService.getUserApi(UserContext.userData.get().getUserId()));
    }

    // TODO : 요청 패쓰배리어블로 userId 들어올 것
    @ValidToken
    @GetMapping("/api/userInfo/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long userId) {
        return ResponseEntity.ok().body(ResponseDto.of(HttpStatus.OK, userService.getUser(userId)));

    }
    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("api/user/image")
    public ResponseEntity<?> userImageUpdate(@RequestParam("photo") MultipartFile imageFile) throws IOException {

       return ResponseEntity.ok().body(userService.uploadUserImage(imageFile,UserContext.userData.get().getUserId()));
    }
    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PutMapping("/api/user")
    public ResponseEntity<Object> updateUserInfo(
            @RequestBody @Valid UserUpdateRequestDto requestDto) {
        if (requestDto.getNickName() == null) {
            return ResponseEntity.badRequest().build();
        }
        userService.updateUser(UserContext.userData.get().getUserId(), requestDto);
        return ResponseEntity.ok().build();
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @DeleteMapping("/api/user")
    public ResponseEntity<Object> userWithDraw() {
        userService.userWithdraw(UserContext.userData.get().getUserId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/user/reset-pwd")
    public ResponseEntity<?> resetPassword(@RequestBody UserPwdResetRequestDto requestDto) throws Exception {
        userService.resetPassword(requestDto);
        return ResponseEntity.ok().body(ResponseDto.of(
                "비밀번호 변경에 성공하였습니다."
        ));
    }
}
