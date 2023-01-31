package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import Maswillaeng.MSLback.dto.post.reponse.UserPostResponseDto;
import Maswillaeng.MSLback.dto.post.request.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public void registerPost(Post post) {

        postRepository.save(post);
        post.getUser().addPost(post);
    }

    @Transactional(readOnly = true)
    public Page<Post> getPostList(int currentPage) {

        return postRepository.findAll(PageRequest.of(
                        currentPage - 1, 20, Sort.Direction.DESC, "createdAt"));
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다."));
    }

    public void updatePost(PostUpdateDto updateDto) {
        Post post = postRepository.findById(updateDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다."));
        post.update(updateDto);
    }

    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new EntityNotFoundException("존재하지 않는 게시물입니다.");
        }
        postRepository.deleteById(postId);
    }

    public Page<Post> getUserPostList(User user, int currentPage) {
        return postRepository.findByUser(user, PageRequest.of(
        currentPage - 1, 20, Sort.Direction.DESC, "createdAt"));
    }
}
