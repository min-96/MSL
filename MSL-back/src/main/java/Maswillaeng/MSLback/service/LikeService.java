package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.common.exception.EntityNotFoundException;
import Maswillaeng.MSLback.domain.entity.*;
import Maswillaeng.MSLback.domain.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@Service
@Transactional
public class LikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    public void savePostLike(Long userId, Long postId) {
        User user = userRepository.findById(userId).get();
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 게시물입니다"));

        if (postLikeRepository.existsByUserAndPost(user, post)) {
            throw new IllegalStateException("해당 게시물에 이미 좋아요를 눌렀습니다");
        }

        PostLike postLike = PostLike.builder()
                .user(user)
                .post(post).build();
        postLikeRepository.save(postLike);
    }

    public void saveCommentLike(Long userId, Long commentId) {
        User user = userRepository.findById(userId).get();
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 댓글입니다."));

        if (commentLikeRepository.existsByUserAndComment(user, comment)) {
            throw new IllegalStateException("해당 댓글에 이미 좋아요를 눌렀습니다");
        }

        CommentLike postLike = CommentLike.builder()
                .user(user)
                .comment(comment).build();
        commentLikeRepository.save(postLike);
    }

    public void deletePostLike(Long userId, Long postId) throws AccessDeniedException {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 게시물입니다"));
        User user = userRepository.findById(userId).get();
        if (!userId.equals(post.getUser().getId())) {
            throw new AccessDeniedException("접근 권한 없음");
        }
        postLikeRepository.deleteByUserAndPost(user, post);

    }

    public void deleteCommentLike(Long userId, Long commentId) throws AccessDeniedException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 게시물입니다"));
        User user = userRepository.findById(userId).get();
        if (!userId.equals(comment.getUser().getId())) {
            throw new AccessDeniedException("접근 권한 없음");
        }

        commentLikeRepository.deleteByUserAndComment(user, comment);
    }
}
