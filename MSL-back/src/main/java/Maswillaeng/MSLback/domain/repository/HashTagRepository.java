package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.HashTag;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    List<HashTag> findByPost(Post post);

    List<HashTag> findByPostAndTagIn(Post selectedPost, List<Tag> tagList);
}
