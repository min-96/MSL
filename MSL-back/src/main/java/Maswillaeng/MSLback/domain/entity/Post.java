package Maswillaeng.MSLback.domain.entity;

import Maswillaeng.MSLback.domain.enums.Category;
import Maswillaeng.MSLback.dto.post.request.PostUpdateDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
    public Post(String title, String content, User user, Category category,int disabled) {
        this.thumbnail = getThumbnail(content);
        this.title = title;
        this.content = content;
        this.user = user;
        this.hits = 1L;
        this.category = category;
        this.disabled = 0;
    }

    private String getThumbnail(String content) {
        Document doc = Jsoup.parse(content);
        Elements imgTags = doc.select("img");
        if (imgTags.size() > 0) {
            Element firstImgTag = imgTags.get(0);
            return firstImgTag.attr("src");
        } else {
            return null;
        }
    }


    public void update(PostUpdateDto postUpdateDto) {
        this.title = postUpdateDto.getTitle();
        this.content = postUpdateDto.getContent();
        this.category = postUpdateDto.getCategory();
        this.thumbnail = getThumbnail(this.content);
    }

    public void setHashTagList(List<HashTag> hashTagList) {
        this.hashTagList = hashTagList;
    }

    public void increaseHits() {
        this.hits = this.hits + 1L;
    }

    public void disablePost(){
        this.disabled = 1;
    }
}
