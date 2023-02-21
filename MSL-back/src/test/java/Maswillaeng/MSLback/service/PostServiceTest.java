package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.PostLike;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.enums.Category;
import Maswillaeng.MSLback.domain.repository.PostLikeRepository;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Test
    @Transactional
    void 인기게시글조회_테스트() {

        // given
        List<User> users = new ArrayList<>();
        List<Post> posts = new ArrayList<>();
        List<PostLike> postLikes = new ArrayList<>();

        // 100명의 유저 만들기
        for (int i = 0; i < 100; i++) {
            User user = User.builder()
                    .nickName(String.valueOf(i))
                    .email(String.valueOf(i))
                    .password(String.valueOf(i))
                    .build();
            userRepository.save(user);
            users.add(user);
        }

        // 10개의 포스트 만들기
        for (int i = 0; i < 10; i++) {
            Post post = Post.builder()
                    .user(users.get(i % 100))
                    .title("post" + i)
                    .content("content" + i)
                    .build();
            postRepository.save(post);
            posts.add(post);
        }

        // 포스트에 좋아요 누르기
        for (Post post : posts) {
            for (int i = 0; i < 60; i++) {
                PostLike postLike = PostLike.builder()
                        .post(post)
                        .user(users.get(i))
                        .build();
                postLikeRepository.save(postLike);
                postLikes.add(postLike);
            }
        }

        // When
        List<PostResponseDto> postResponseDtos = postService.getPostList(Category.BEST);

        // Then
        assertThat(postResponseDtos.size()).isEqualTo(posts.size());

    }

}