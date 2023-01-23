package Maswillaeng.MSLback.dto.post.reponse;

import Maswillaeng.MSLback.domain.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter

public class PostResponseDto {
    private Long postId;
    private String thumbnail;
    private String title;
    private String content;

    public PostResponseDto(Post post) {
        postId = post.getId();
        thumbnail = post.getThumbnail();
        title = post.getTitle();
        content = post.getContent();
    }
}
