package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import Maswillaeng.MSLback.service.UserService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;


    @AuthCheck(role = AuthCheck.Role.USER)
    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo() {
        return ResponseEntity.ok().body(
                userService.getUser(UserContext.userData.get().getUserId()));
    }


    @AuthCheck(role = AuthCheck.Role.USER)
    @PutMapping("/user")
    public ResponseEntity<Object> updateUserInfo(
            @RequestBody @Valid UserUpdateRequestDto requestDto) {
        if (requestDto.getPassword() == null && requestDto.getNickName() == null) {
            return ResponseEntity.badRequest().build();
        }
        userService.updateUser(UserContext.userData.get().getUserId(), requestDto);
        return ResponseEntity.ok().build();
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @DeleteMapping("/user")
    public ResponseEntity<Object> userWithDraw() {
        userService.userWithdraw(UserContext.userData.get().getUserId());
        return ResponseEntity.ok().build();
    }
}
