package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.service.LikeService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.UserContext;
import Maswillaeng.MSLback.utils.auth.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

import static Maswillaeng.MSLback.common.message.SuccessMessage.*;

@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/api/post-like/{postId}")
    public ResponseEntity<Object> savePostLike(@PathVariable Long postId) {
        likeService.savePostLike(UserContext.userData.get().getUserId(), postId);
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_SAVE_POST_LIKE));
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/api/comment-like/{commentId}")
    public ResponseEntity<Object> saveCommentLike(@PathVariable Long commentId) {
        likeService.saveCommentLike(UserContext.userData.get().getUserId(), commentId);
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_SAVE_COMMENT_LIKE));
    }

    @ValidToken
    @DeleteMapping("/api/post-like/{postId}")
    public ResponseEntity<Object> deletePostLike(@PathVariable Long postId) {
        likeService.deletePostLike(UserContext.userData.get().getUserId(), postId);
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_DELETE_POST_LIKE));
    }

    @ValidToken
    @DeleteMapping("/api/comment-like/{commentId}")
    public ResponseEntity<Object> deleteCommentLike(@PathVariable Long commentId) {
        likeService.deleteCommentLike(UserContext.userData.get().getUserId(), commentId);
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_DELETE_COMMENT_LIKE));
    }
}
