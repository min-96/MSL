package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.HashTag;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.Tag;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.enums.Category;
import Maswillaeng.MSLback.domain.repository.*;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import Maswillaeng.MSLback.dto.post.request.PostRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostQueryRepository postQueryRepository;
    private final TagRepository tagRepository;
    private final HashTagRepository hashTagRepository;

    public void registerPost(Long userId, PostRequestDto postRequestDto) {
        User user = userRepository.findById(userId).get();
        Post post = postRequestDto.toEntity(user);

        List<HashTag> resultHashTagList = getHashTagList(postRequestDto.getHashTagList(), post);

        post.setHashTagList(resultHashTagList);

        postRepository.save(post);
    }

    private List<HashTag> getHashTagList(List<String> hashTagList, Post post) {

        List<Tag> existHashTagList = tagRepository.findByNameList(hashTagList);

        return hashTagList.stream()
                .map(tagName -> existHashTagList.stream()
                        .filter(t -> t.getName().equals(tagName))
                        .findFirst()
                        .map(tag -> new HashTag(tag, post))
                        .orElseGet(() -> new HashTag(new Tag(tagName), post)))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostList(Category category) {

        return postQueryRepository.findAllPostByCategory(category);
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long postId) {
        return postRepository.findByIdFetchJoin(postId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다."));
    }

    public void updatePost(Long userId, PostUpdateDto updateDto) throws Exception {
        Post selectedPost = postRepository.findById(updateDto.getPostId()).get();

        if (Objects.equals(selectedPost.getUser().getId(), userId)) {
            selectedPost.update(updateDto);
//            postRepository.save(selectedPost);
        } else {
            throw new Exception("접근 권한 없음");
        }
    }

    public void deletePost(Long userId, Long postId) throws ValidationException {
        Post post = postRepository.findById(postId).get();
        if (!Objects.equals(userId, post.getUser().getId())) {
            postRepository.delete(post);
        }
        throw new ValidationException("접근 권한 없음");
    }

    public Page<Post> getUserPostList(Long userId, int currentPage) {
        return postRepository.findByUserIdFetchJoin(userId, PageRequest.of(
        currentPage - 1, 20, Sort.Direction.DESC, "createdAt"));
    }
}
