package Maswillaeng.MSLback.dto.post.request;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class PostRequestDto {

    @NotBlank
    private String title;

    private String thumbnail;

    @NotNull
    private String content;


    public Post toEntity(User user) {
        return Post.builder()
                .user(user)
                .title(title)
                .thumbnail(thumbnail)
                .content(content)
                .build();
    }
}
