package Maswillaeng.MSLback.dto.post.reponse;

import Maswillaeng.MSLback.domain.entity.HashTag;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.dto.comment.response.CommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//TODO : 좋아요 수 likeNumber, reported
@NoArgsConstructor
@Getter @Setter
public class PostDetailResponseDto {
    private Long postId;
    private Long userId;
    private String nickName;
    private String userImage;
    private String title;
    private LocalDateTime createdAt;
    private int likeNumber;
    private boolean reported;
    private String content;
    private boolean isLiked;
    private List<String> hashTagList;
    private List<CommentResponseDto> commentList;

    public PostDetailResponseDto(Post post, Long userId) {
        this.postId = post.getId();
        this.userId = post.getUser().getId();
        this.nickName = post.getUser().getNickName();
        this.userImage = post.getUser().getUserImage();
        this.title = post.getTitle();
        this.createdAt = post.getCreatedAt();
        this.content = post.getContent();
        this.likeNumber = 50;
        this.reported = false;
        this.isLiked = post.getPostLikeList().stream().anyMatch(postLike ->
                postLike.getUser().getId().equals(userId));
        this.hashTagList = post.getHashTagList().stream().map(hashTag ->
                hashTag.getTag().getName()).toList();
        this.commentList = post.getCommentList().stream()
                .filter(comment -> comment.getParentId() == null)
                .map(comment -> new CommentResponseDto(comment, comment.getCommentLikeList().size(), userId))
                .toList();
    }

    public PostDetailResponseDto(Post post) {
        this.postId = post.getId();
        this.userId = post.getUser().getId();
        this.nickName = post.getUser().getNickName();
        this.userImage = post.getUser().getUserImage();
        this.title = post.getTitle();
        this.createdAt = post.getCreatedAt();
        this.content = post.getContent();
        this.isLiked = false;
        this.hashTagList = post.getHashTagList().stream().map(hashTag ->
                hashTag.getTag().getName()).toList();
        this.commentList = post.getCommentList().stream()
                .filter(comment -> comment.getParentId() == null)
                .map(comment -> new CommentResponseDto(comment, comment.getCommentLikeList().size()))
                .toList();
    }
}
