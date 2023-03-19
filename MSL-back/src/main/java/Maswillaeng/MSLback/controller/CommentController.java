package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.comment.request.CommentRequestDto;
import Maswillaeng.MSLback.dto.comment.request.CommentUpdateRequestDto;
import Maswillaeng.MSLback.dto.comment.request.RecommentRequestDto;
import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.service.CommentService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.UserContext;
import Maswillaeng.MSLback.utils.auth.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

import static Maswillaeng.MSLback.common.message.SuccessMessage.*;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/api/comment")
    public ResponseEntity<?> saveComment(@RequestBody CommentRequestDto commentRequestDto) {

        commentService.registerComment(UserContext.userData.get().getUserId(), commentRequestDto);

        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_SAVE_COMMENT));
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/api/recomment")
    public ResponseEntity<?> saveRecomment(@RequestBody RecommentRequestDto recommentRequestDto) {

        commentService.registerRecomment(UserContext.userData.get().getUserId(), recommentRequestDto);

        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_SAVE_RECOMMENT));
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PutMapping("/api/comment")
    public ResponseEntity<?> updateComment(@RequestBody CommentUpdateRequestDto updateRequestDto) throws AccessDeniedException {

        commentService.updateComment(updateRequestDto);

        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_UPDATE_COMMENT));
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @DeleteMapping("/api/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) throws AccessDeniedException {

        commentService.deleteComment(commentId);

        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_DELETE_COMMENT));
    }

    @ValidToken
    @GetMapping("/api/recomment/{parentId}")
    public ResponseEntity<?> getRecommentList(@PathVariable Long parentId) {
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_GET_RECOMMENT_LIST,
                commentService.getRecommentList(parentId)));
    }

}
