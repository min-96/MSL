package Maswillaeng.MSLback.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", insertable = false, updatable = false)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", insertable = false, updatable = false)
    private Comment parentId;

    @OneToMany(mappedBy = "parentId", fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>(); // 어차피 api 요청이 올때마다 준다면, 관리할 필요가 있을까? 없는게 낫다고 봄

    @Builder
    public Comment(Long commentId, Post post, User user, String content, Comment parentId) {
        this.commentId = commentId;
        this.post = post;
        this.user = user;
        this.content = content;
        this.parentId = parentId;
    }
}
