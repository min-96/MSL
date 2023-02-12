package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.comment.request.CommentRequestDto;
import Maswillaeng.MSLback.dto.comment.request.CommentUpdateRequestDto;
import Maswillaeng.MSLback.dto.comment.request.RecommentRequestDto;
import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.service.CommentService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/comment")
    public ResponseEntity<?> saveComment(@RequestBody CommentRequestDto commentRequestDto) {

        commentService.registerComment(UserContext.userId.get(), commentRequestDto);

        return ResponseEntity.ok().body(ResponseDto.of(
                "댓글 등록 성공", null // TODO 댓글 입력시 응답 다시 확인
        ));
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/recomment")
    public ResponseEntity<?> saveRecomment(@RequestBody RecommentRequestDto recommentRequestDto) {

        commentService.registerRecomment(UserContext.userId.get(), recommentRequestDto);

        return ResponseEntity.ok().body(ResponseDto.of(
                "댓글 등록 성공", null // TODO 대댓글 입력시 응답 다시 확인
        ));
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @PutMapping("/comment")
    public ResponseEntity<?> updateComment(@RequestBody CommentUpdateRequestDto updateRequestDto) throws ValidationException {

        commentService.updateComment(updateRequestDto);

        return ResponseEntity.ok().body(ResponseDto.of(
                "댓글 수정 성공", null
        ));
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) throws ValidationException {

        commentService.deleteComment(commentId);

        return ResponseEntity.ok().body(ResponseDto.of(
                "댓글 삭제 성공", null
        ));
    }
}
