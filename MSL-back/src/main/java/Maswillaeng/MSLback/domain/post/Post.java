package Maswillaeng.MSLback.domain.post;

import Maswillaeng.MSLback.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String thumbnail;

    private String title;

    private String content;

    @Builder
    public Post(String thumbnail, String title, String content) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.content = content;
    }
}
