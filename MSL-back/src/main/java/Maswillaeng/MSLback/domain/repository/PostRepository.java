package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.user where p.id = :id")
    Optional<Post> findByIdFetchJoin(@Param("id") Long id);

    @Query(value = "select p from Post p join fetch p.user",
            countQuery = "select count(p) from Post p")
    Page<Post> findAllFetchJoin(Pageable pageable);

    @Query(value = "select p from Post p join fetch p.user u where u.id = :userId",
            countQuery = "select count(p) from Post p where p.user.id = :userId")
    Page<Post> findByUserIdFetchJoin(@Param("userId") Long userId, PageRequest pageable);

}
