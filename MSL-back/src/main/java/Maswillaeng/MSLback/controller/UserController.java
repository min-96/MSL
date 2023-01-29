package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.user.request.UserJoinDto;
import Maswillaeng.MSLback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
