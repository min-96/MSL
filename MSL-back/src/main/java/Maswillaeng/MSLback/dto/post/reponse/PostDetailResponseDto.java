package Maswillaeng.MSLback.dto.post.reponse;

import Maswillaeng.MSLback.domain.entity.HashTag;
import Maswillaeng.MSLback.dto.comment.response.CommentResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class PostDetailResponseDto {
    private Long postId;
    private Long userId;
    private String nickName;
    private String userImage;
    private String title;
    private LocalDateTime createdAt;
    private String content;
    private boolean isLiked;
    private int commentNumber;
    private List<HashTag> HashTagList;
    private List<CommentResponseDto> commentList;
}
