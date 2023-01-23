package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.post.Post;
import Maswillaeng.MSLback.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public void registerPost(Post post) {

        postRepository.save(post);
    }

    public Page<Post> getPostList(int currentPage) {

        return postRepository.findAll(PageRequest.of(
                        currentPage - 1, 10, Sort.Direction.DESC, "createdAt"));
    }
}
