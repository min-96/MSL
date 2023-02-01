package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import Maswillaeng.MSLback.dto.post.reponse.UserPostResponseDto;
import Maswillaeng.MSLback.dto.post.request.PostRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostUpdateDto;
import Maswillaeng.MSLback.service.PostService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @AuthCheck
    @PostMapping("/post")
    public ResponseEntity<?> savePost(@RequestBody @Valid PostRequestDto requestDto) {
        User user = UserContext.currentMember.get();
        postService.registerPost(requestDto.toEntity(user));

        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK
        ));
    }

    @GetMapping("/post/page")
    public ResponseEntity<?> getPostList(@RequestParam int currentPage) {

        Page<PostResponseDto> postList = postService.getPostList(currentPage)
                                        .map(PostResponseDto::new);

        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK, postList));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {

        Post post = postService.getPostById(postId);

        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK, new PostResponseDto(post)
        ));
    }


    // 프론트에서 검증하고 줄텐데, 백에서도 검증을 해야하나?
    // 검증해야한다면 nickname이나 userId를 받아야 할듯
    @AuthCheck
    @PutMapping("/post")
    public ResponseEntity<?> updatePost(@RequestBody @Valid PostUpdateDto updateDto) throws ValidationException {
        User user = UserContext.currentMember.get();
        postService.updatePost(user, updateDto);
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK
        ));
    }

    // 이것도 검증 필요한지
    @AuthCheck
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) throws ValidationException {
        User user = UserContext.currentMember.get();
        postService.deletePost(user, postId);
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK
        ));
    }

    @AuthCheck
    @GetMapping("/userPostList")
    public ResponseEntity<?> getUserPostList(@RequestParam int currentPage) {
        User user = UserContext.currentMember.get();
        return ResponseEntity.ok().body(
                postService.getUserPostList(user, currentPage)
                            .map(UserPostResponseDto::new));
    }

}
