package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.user where p.id = :id")
    Optional<Post> findByIdFetchJoin(@Param("id") Long id);

    @Query("select distinct p from Post p "
            + "join fetch p.user "
//            + "left join fetch p.likeList "
//            + "left join fetch p.commentList "
            + "order by p.createdAt DESC")
    List<Post> findAllFetchJoin();


    @Query(value = "select p from Post p join fetch p.user u where u.id = :userId",
            countQuery = "select count(p) from Post p where p.user.id = :userId")
    Page<Post> findByUserIdFetchJoin(@Param("userId") Long userId, PageRequest pageable);

}
