package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.common.exception.AlreadyExistException;
import Maswillaeng.MSLback.common.exception.EntityNotFoundException;
import Maswillaeng.MSLback.common.exception.NotAuthorizedException;
import Maswillaeng.MSLback.domain.entity.*;
import Maswillaeng.MSLback.domain.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service

public class LikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void savePostLike(Long userId, Long postId) {
        User user = userRepository.findById(userId).get();
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException(Post.class.getSimpleName()));

        if (postLikeRepository.existsByUserAndPost(user, post)) {
            throw new AlreadyExistException(PostLike.class.getSimpleName());
        }

        PostLike postLike = PostLike.builder()
                .user(user)
                .post(post).build();
        postLikeRepository.save(postLike);
    }

    @Transactional
    public void saveCommentLike(Long userId, Long commentId) {
        User user = userRepository.findById(userId).get();
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException(Comment.class.getSimpleName()));

        if (commentLikeRepository.existsByUserAndComment(user, comment)) {
            throw new AlreadyExistException(Comment.class.getSimpleName());
        }

        CommentLike postLike = CommentLike.builder()
                .user(user)
                .comment(comment).build();
        commentLikeRepository.save(postLike);
    }

    @Transactional
    public void deletePostLike(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new EntityNotFoundException(Post.class.getSimpleName()));
        User user = userRepository.findById(userId).get();
        if (!userId.equals(post.getUser().getId())) {
            throw new NotAuthorizedException(PostLike.class.getSimpleName());
        }
        postLikeRepository.deleteByUserAndPost(user, post);

    }

    @Transactional
    public void deleteCommentLike(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException(Comment.class.getSimpleName()));
        User user = userRepository.findById(userId).get();
        if (!userId.equals(comment.getUser().getId())) {
            throw new NotAuthorizedException(CommentLike.class.getSimpleName());
        }

        commentLikeRepository.deleteByUserAndComment(user, comment);
    }
}
