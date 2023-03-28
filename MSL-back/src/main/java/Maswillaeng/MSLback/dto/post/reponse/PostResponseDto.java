package Maswillaeng.MSLback.dto.post.reponse;

import Maswillaeng.MSLback.domain.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
public class PostResponseDto {
    private Long postId;

    private Long userId;
    private String nickName;
    private String userImage;
    private String thumbnail;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private Long likeCnt;
    private Long commentCnt;
    private Long hits;


    public PostResponseDto(Post post, Long likeCnt, Long commentCnt) {
        postId = post.getId();
        userId = post.getUser().getId();
        nickName = post.getUser().getNickName();
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
    public PostResponseDto(long postId, long userId, String nickName, String userImage, String thumbnail
            ,String title, String content, long hits, LocalDateTime createdAt, LocalDateTime modifiedAt
            ,long likeCnt, long commentCnt) {
        this.postId = postId;
        this.userId = userId;
        this.nickName = nickName;
        this.userImage = userImage;
        this.thumbnail = thumbnail;
        this.title = title;
        this.hits = hits;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.likeCnt = (long) likeCnt;
        this.commentCnt = (long) commentCnt;
    }


}
