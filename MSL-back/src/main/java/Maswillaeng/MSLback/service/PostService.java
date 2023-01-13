package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.post.Post;
import Maswillaeng.MSLback.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public void registerPost(Post post) {

        postRepository.save(post);
    }

}
