package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.HashTag;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.Tag;
import Maswillaeng.MSLback.dto.common.BestTagDto;
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

    @Query(value = "select h from HashTag h where h.tag.name = :removeHashTag")
    List<HashTag> findByNames(@Param("removeHashTag") String removeHashTag);

    @Modifying
    @Query(value = "delete from HashTag h where h.post.id = :postId and h.tag.name in :removeHashTag")
    void deleteByName(@Param("removeHashTag") List<String> removeHashTag, @Param("postId") Long postId);

//    @Query("select h from HashTag h join fetch h.post where h.tag.name =:tagName")
//    List<HashTag> findByTagNames(@Param("tagName") String tagName);

    @Query("select new Maswillaeng.MSLback.dto.common.BestTagDto(h.tag.name,count(h.tag.name)) from HashTag h group by h.tag.name order by count(h.tag.name) DESC")
    List<BestTagDto> findByBestTagName();
}
