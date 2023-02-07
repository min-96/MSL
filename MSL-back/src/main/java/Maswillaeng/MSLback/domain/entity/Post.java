package Maswillaeng.MSLback.domain.entity;

import Maswillaeng.MSLback.dto.post.request.PostUpdateDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    private Long hits; // int가 21억까지 표현 가능한데 조회수가 21억을 넘을 일이 있을까?

    @Builder
    public Post(String thumbnail, String title, String content, User user) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.content = content;
        this.user = user;
        this.hits = 1L;
    }

    public void update(PostUpdateDto postUpdateDto) {
        this.thumbnail = postUpdateDto.getThumbnail();
        this.title = postUpdateDto.getTitle();
        this.content = postUpdateDto.getContent();
    }

    // 연관관계 편의 메서드
    private void changeCategory(Category category) {
        this.category = category;
    }
}
