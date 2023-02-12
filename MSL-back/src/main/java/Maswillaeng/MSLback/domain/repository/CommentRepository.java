package Maswillaeng.MSLback.domain.repository;

import Maswillaeng.MSLback.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {


}
