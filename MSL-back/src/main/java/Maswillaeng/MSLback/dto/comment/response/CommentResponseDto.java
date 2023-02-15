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
//    private String nickName;
//    private String userImage;
    private String content;
    private LocalDateTime createAt;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.createAt = comment.getCreatedAt();
    }
//    private boolean userLikeYn;
//    private int likeCount;


}
