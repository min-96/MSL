package Maswillaeng.MSLback.dto.post.reponse;

import Maswillaeng.MSLback.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class UserPostResponseDto {
    private Long postId;
    private String nickName;
    private String ThumbNail;
    private String title;
    private String content;

    public UserPostResponseDto(Post post) {
        this.postId = post.getId();
        this.nickName = post.getUser().getNickName();
        this.ThumbNail = post.getThumbnail();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
