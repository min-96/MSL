package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select p from Post p join fetch p.user u where u.id = :userId",
            countQuery = "select count(p) from Post p where p.user.id = :userId")
    Page<Post> findByUserIdFetchJoin(@Param("userId") Long userId, PageRequest pageable);


    @Query(value = "delete from Post p where p.id in :postList")
    void deleteAllById(@Param("postList") List<Post> postList);

//    @Query(value = "select p, p.commentList.size(), p.postLikeList.size() from Post p inner join  p.hashTagList h join  p.user u where h.tag.name = :tagName")
    @Query("select new Maswillaeng.MSLback.dto.post.reponse.PostResponseDto(" +
            "p.id, p.user.id, p.user.nickName, p.user.userImage, p.thumbnail," +
            "p.title, p.content, p.hits, p.createdAt, p.modifiedAt, p.postLikeList.size, p.commentList.size)" +
            " from Post p join p.hashTagList h where h.tag.name = :tagName order by p.createdAt DESC")
    List<PostResponseDto> findByTest(@Param("tagName") String tagName);
}
