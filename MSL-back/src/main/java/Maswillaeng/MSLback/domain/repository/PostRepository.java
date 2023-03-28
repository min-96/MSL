package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "delete from Post p where p.id in :postList")
    void deleteAllById(@Param("postList") List<Post> postList);


    @Query(value = "SELECT new Maswillaeng.MSLback.dto.post.reponse.PostResponseDto(p.id, u.id, u.nickName, u.userImage, p.thumbnail, p.title, p.content, p.hits, p.createdAt, p.modifiedAt, (SELECT COUNT(pl) FROM PostLike pl WHERE pl.post.id = p.id), (SELECT COUNT(c) FROM Comment c WHERE c.post.id = p.id)) FROM Post p INNER JOIN p.user u " +
            "ORDER BY p.createdAt DESC")
    List<PostResponseDto> findBySubList();

}
