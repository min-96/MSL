package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.HashTag;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    List<HashTag> findByPost(Post post);

    List<HashTag> findByPostAndTagIn(Post selectedPost, List<Tag> tagList);

    @Modifying
    @Query("delete from HashTag h where h.post.id = :postId")
    void deleteByPostId(@Param("postId") Long postId);
}
