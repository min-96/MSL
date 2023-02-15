package Maswillaeng.MSLback.dto.post.reponse;

import Maswillaeng.MSLback.domain.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter

public class PostResponseDto {
    private Long postId;

    private Long userId;
    private String nickname;
    private String userImage;
    private String thumbnail;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private int likeCnt;
    private int commentCnt;
    private Long hits;

    public PostResponseDto(Post post, int likeCnt, int commentCnt) {
        postId = post.getId();
        userId = post.getUser().getId();
        nickname = post.getUser().getNickName();
        userImage = post.getUser().getUserImage();
        thumbnail = post.getThumbnail();
        title = post.getTitle();
        hits = post.getHits();
        content = post.getContent();
        createdAt = post.getCreatedAt();
        modifiedAt = post.getModifiedAt();
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
    }
}
