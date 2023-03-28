package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.service.FollowService;
import Maswillaeng.MSLback.utils.auth.UserContext;
import Maswillaeng.MSLback.utils.auth.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static Maswillaeng.MSLback.common.message.SuccessMessage.*;

@RequiredArgsConstructor
@RestController
public class FollowController { // TODO : 메서드 명 변경

    private final FollowService followService;

    @ValidToken
    @PostMapping("/api/following/{userId}")
    public ResponseEntity<?> following(@PathVariable Long userId) {
        followService.following(UserContext.userData.get().getUserId(), userId);
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_FOLLOW_USER));
    }

    @ValidToken
    @DeleteMapping("api/following/{userId}")
    public ResponseEntity<?> followingDelete(@PathVariable Long userId) {
        followService.unFollow(UserContext.userData.get().getUserId(), userId);
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_UNFOLLOW_USER));
    }

    @ValidToken
    @GetMapping("/api/following-list/{userId}")
    public ResponseEntity<?> followingList(@PathVariable Long userId) {
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_GET_FOLLOWING_LIST,
                followService.getFollowingList(userId)));
    }

    @ValidToken
    @GetMapping("/api/follower-list/{userId}")
    public ResponseEntity<?> followerList(@PathVariable Long userId) {
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_GET_FOLLOWER_LIST,
                followService.getFollowerList(userId)));
    }

    @ValidToken
    @GetMapping("/api/new-pid")
    public ResponseEntity<?> followingPostList(@RequestParam int page) {
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_GET_FOLLOWING_POST_LIST,
                followService.getFollowingPostList(UserContext.userData.get().getUserId(), page)));
    }

}
