package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.post.Post;
import Maswillaeng.MSLback.domain.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Test
    @Transactional
    void registerPostTest() {
        //given
        Post testPost = Post.builder()
                .title("test")
                .build();
        //when
        postService.registerPost(testPost);
        Post findPost = postRepository.findById(1L).get();

        //then
        Assertions.assertThat(findPost.getTitle()).isEqualTo(testPost.getTitle());
    }

}