package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.domain.repository.UserRepository;
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
import javax.xml.bind.ValidationException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public void registerPost(Post post) {

        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Page<Post> getPostList(int currentPage) {

        return postRepository.findAllFetchJoin(PageRequest.of(
                        currentPage - 1, 20, Sort.Direction.DESC, "createdAt"));
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long postId) {
        return postRepository.findByIdFetchJoin(postId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다."));
    }

    public void updatePost(User currentUser, PostUpdateDto updateDto) throws ValidationException {
        Post post = postRepository.findById(updateDto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다."));
        if (Objects.equals(currentUser.getId(), post.getUser().getId())) {
            post.update(updateDto);
        } else {
            throw new ValidationException("접근 권한 없음");
        }
    }

    public void deletePost(User currentUser, Long postId) throws ValidationException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다."));
        if (Objects.equals(currentUser.getId(), post.getUser().getId())) {
            postRepository.delete(post);
        } else {
            throw new ValidationException("접근 권한 없음");
        }
    }

    public Page<Post> getUserPostList(User user, int currentPage) {
        return postRepository.findByUser(user, PageRequest.of(
        currentPage - 1, 20, Sort.Direction.DESC, "createdAt"));
    }
}
