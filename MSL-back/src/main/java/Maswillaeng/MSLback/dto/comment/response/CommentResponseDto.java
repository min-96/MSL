package Maswillaeng.MSLback.dto.comment.response;

import Maswillaeng.MSLback.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private Long userId;
    private String nickName;
    private String userImage;
    private String content;
    private LocalDateTime createAt;
    private boolean isLiked;
    private int like;

    public CommentResponseDto(Comment comment, int likeCnt) {
        commentId = comment.getId();
        content = comment.getContent();
        createAt = comment.getCreatedAt();
        userId = comment.getUser().getId();
        nickName = comment.getUser().getNickName();
        userImage = comment.getUser().getUserImage();
        this.like = likeCnt;
    }
}
