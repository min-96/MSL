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

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @PostMapping
    public ResponseEntity<?> savePost(@RequestBody @Valid PostRequestDto requestDto) {

        postService.registerPost(requestDto.toEntity());

        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK
        ));
    }

    // page라는 주소가 필요한가? 없어도 될듯
    @GetMapping("/post/page")
    public ResponseEntity<?> getPostList(@RequestParam int currentPage) {


        // Page로 그냥 반환하는게 낫겠다.
        // 토탈 카운트를 따로 묶어서 주려면 DTO가 하나 더 필요하고 필요 이상으로 복잡해진다.
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

    // 업데이트가 반환값이 필요한가?
    // 포스트 아이디는 바디에선 필요 없다
    @PutMapping("/post")
    public ResponseEntity<?> updatePost(@RequestBody @Valid PostUpdateDto updateDto) {
        postService.updatePost(updateDto);
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK
        ));
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
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
