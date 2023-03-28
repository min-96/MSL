package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.common.exception.EntityNotFoundException;
import Maswillaeng.MSLback.common.exception.NotAuthorizedException;
import Maswillaeng.MSLback.domain.entity.Comment;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.CommentRepository;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.comment.request.CommentRequestDto;
import Maswillaeng.MSLback.dto.comment.request.CommentUpdateRequestDto;
import Maswillaeng.MSLback.dto.comment.request.RecommentRequestDto;
import Maswillaeng.MSLback.dto.comment.response.CommentResponseDto;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void registerComment(Long userId, CommentRequestDto dto) {
        Post post = postRepository.findById(dto.getPostId()).orElseThrow(
                () -> new EntityNotFoundException(Post.class.getSimpleName())
        );
        User user = userRepository.findById(userId).get();

        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(dto.getContent())
                .build();
        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(CommentUpdateRequestDto dto) {
        Comment comment = commentRepository.findById(dto.getCommentId()).orElseThrow(
                () -> new EntityNotFoundException(Comment.class.getSimpleName())
        );
        validateUser(UserContext.userData.get().getUserId(), comment);
        comment.updateComment(dto.getContent());
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new EntityNotFoundException(Comment.class.getSimpleName())
        );
        validateUser(UserContext.userData.get().getUserId(), comment);
        commentRepository.delete(comment);
    }

    public void validateUser(Long userId, Comment comment) {
        if (!comment.getUser().getId().equals(userId)) {
            throw new NotAuthorizedException(Comment.class.getSimpleName());
        }
    }

    @Transactional
    public void registerRecomment(Long userId, RecommentRequestDto dto) {
        Comment parentComment = commentRepository.findById(dto.getParentId()).orElseThrow(
                () -> new EntityNotFoundException(Comment.class.getSimpleName())
        );
        User user = userRepository.findById(userId).get();
        Comment recomment = Comment.builder()
                .post(parentComment.getPost())
                .user(user)
                .content(dto.getContent())
                .parent(parentComment)
                .build();
        commentRepository.save(recomment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getRecommentList(Long parentId) {
        List<Comment> recommentList = commentRepository.findByParentId(parentId);

        if (UserContext.userData.get() == null) {
            return recommentList.stream().map(
                    comment -> new CommentResponseDto(
                            comment, comment.getCommentLikeList().size())).toList();
        }

        Long userId = UserContext.userData.get().getUserId();
        return recommentList.stream().map(
                comment -> new CommentResponseDto(
                        comment, comment.getCommentLikeList().size(), userId)).toList();

    }
}
