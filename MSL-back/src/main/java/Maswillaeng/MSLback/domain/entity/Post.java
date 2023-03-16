package Maswillaeng.MSLback.domain.entity;

import Maswillaeng.MSLback.domain.enums.Category;
import Maswillaeng.MSLback.dto.post.request.PostUpdateDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "posts")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String thumbnail;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ColumnDefault("0")
    private Long hits;

    @ColumnDefault("0")
    private int disabled;

    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY)
    private Set<PostLike> postLikeList = new HashSet<>();

    @OneToMany(mappedBy = "post",fetch = FetchType.LAZY)
    private Set<Comment> commentList = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private List<HashTag> hashTagList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private List<Report> reportList = new ArrayList<>();

    @Builder
    public Post(String thumbnail, String title, String content, User user, Category category,int disabled) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.content = content;
        this.user = user;
        this.hits = 1L;
        this.category = category;
        this.disabled = 0;
    }

    public void update(PostUpdateDto postUpdateDto) {
        this.thumbnail = postUpdateDto.getThumbnail();
        this.title = postUpdateDto.getTitle();
        this.content = postUpdateDto.getContent();
        this.category = postUpdateDto.getCategory();
    }

    public void setHashTagList(List<HashTag> hashTagList) {
        this.hashTagList = hashTagList;
    }

    public void increaseHits() {
        this.hits = this.hits + 1L;
    }
}
