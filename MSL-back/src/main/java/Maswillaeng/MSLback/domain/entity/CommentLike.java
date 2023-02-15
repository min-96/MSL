package Maswillaeng.MSLback.domain.entity;

import Maswillaeng.MSLback.domain.entity.key.CommentLikeId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike {

    @EmbeddedId
    private CommentLikeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @MapsId("postId")
    private Post post;

    @Builder
    public CommentLike(User user, Post post) {
        this.id = new CommentLikeId(user.getId(), post.getId());
        this.user = user;
        this.post = post;
    }
}