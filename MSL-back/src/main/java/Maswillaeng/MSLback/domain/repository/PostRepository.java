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

    @EntityGraph(attributePaths = {"user"})
    Page<Post> findById(Long id, Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Page<Post> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Page<Post> findByUser(User user, PageRequest pageable);

//    @Query 방식 + count 쿼리 분리 테스트 -> @Query 방식으로 페이징을 하려면 countQuery를 반드시 분리해야 한다.
//    @Query(value = "select p from Post p join fetch p.user where p.user.id = :userId",
//            countQuery = "select count(p) from Post p")
//    Page<Post> findByUserIn(@Param("userId") Long userId, Pageable pageable);
}
