package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"user"})
    Page<Post> findById(Long id, Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Page<Post> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Page<Post> findByUser(User user, PageRequest pageable);
}
