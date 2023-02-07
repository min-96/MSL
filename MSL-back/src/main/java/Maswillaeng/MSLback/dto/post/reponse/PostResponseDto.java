package Maswillaeng.MSLback.dto.post.reponse;

import Maswillaeng.MSLback.domain.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter

public class PostResponseDto {
    private Long postId;

    private String nickname;
    private String thumbnail;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
        postId = post.getId();
        thumbnail = post.getThumbnail();
        title = post.getTitle();
        content = post.getContent();
        nickname = post.getUser().getNickName();
        createdAt = post.getCreatedAt();
        modifiedAt = post.getModifiedAt();
    }
}
