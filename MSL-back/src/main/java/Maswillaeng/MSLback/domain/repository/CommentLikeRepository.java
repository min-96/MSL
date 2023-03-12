package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Comment;
import Maswillaeng.MSLback.domain.entity.CommentLike;
import Maswillaeng.MSLback.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    boolean existsByUserAndComment(User user, Comment comment);

    void deleteByUserAndComment(User user, Comment comment);
}
