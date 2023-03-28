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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

import static Maswillaeng.MSLback.common.message.SuccessMessage.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @ValidToken
    @GetMapping("/api/user")
    public ResponseEntity<?> getUserApi() {

        if (UserContext.userData.get() == null) {
            return ResponseEntity.ok().body(ResponseDto.of(
                    SUCCESS_GET_LOGGED_IN_USER_INFO,
                    new UserApiResponse(false)));
        }

        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_GET_LOGGED_IN_USER_INFO,
                userService.getUserApi(UserContext.userData.get().getUserId())));
    }

    @ValidToken
    @GetMapping("/api/userInfo/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long userId) {
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_GET_USER_INFO,
                userService.getUserInfo(userId)));
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/api/user/image")
    public ResponseEntity<?> userImageUpdate(@RequestParam("photo") MultipartFile imageFile) throws IOException {
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_UPDATE_USER_IMAGE,
                userService.uploadUserImage(imageFile, UserContext.userData.get().getUserId())));
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PutMapping("/api/user")
    public ResponseEntity<Object> updateUserInfo(@RequestBody @Valid UserUpdateRequestDto requestDto) {

        userService.updateUser(UserContext.userData.get().getUserId(), requestDto);

        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_UPDATE_USER_INFO));
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @DeleteMapping("/api/user")
    public ResponseEntity<Object> userWithDraw() {

        userService.withdrawUser(UserContext.userData.get().getUserId());

        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_DELETE_USER));
    }

    @PutMapping("/api/user/reset-pwd")
    public ResponseEntity<?> resetPassword(@RequestBody UserPwdResetRequestDto requestDto) {

        userService.resetPassword(requestDto);

        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_RESET_USER_PASSWORD));
    }
}
