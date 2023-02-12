package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.comment.CommentRequestDto;
import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.service.CommentService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/comment")
    public ResponseEntity<?> saveComment(@RequestBody CommentRequestDto commentRequestDto) {

        commentService.registerComment(UserContext.userId.get(), commentRequestDto);

        return ResponseEntity.ok().body(ResponseDto.of(
                "댓글 등록 성공", null // TODO 댓글 입력시 새로운 댓글을 포함한 댓글 리스트를 보내줘야 함
        ));
    }
}
