package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.HashTag;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {



    List<HashTag> findByPost(Post post);

    List<HashTag> findByPostAndTagIn(Post selectedPost, List<Tag> tagList);

    @Modifying
    @Query("delete from HashTag h where h.post.id = :postId")
    void deleteByPostId(@Param("postId") Long postId);

//    @Query(value = "select h.tag.name, count(*) from HashTag h where h.tag.name in :removeHashTag group by h.tag.name")
//    List<Object[]> countByNames(@Param("removeHashTag") List<String> removeHashTag);

    @Query(value = "select h from HashTag h where h.tag.name = :removeHashTag")
    List<HashTag> findByNames(@Param("removeHashTag") String removeHashTag);
//    @Query(value = "delete from HashTag h where h.post.id = :postId and h.tag.name=:toString")
//    void deleteByHashTag(@Param("toString") String toString,@Param("postId") Long postId);


}
