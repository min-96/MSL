package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.Post;
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

    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/post")
    public ResponseEntity<?> savePost(@RequestBody @Valid PostRequestDto requestDto) {

        postService.registerPost(UserContext.userId.get(), requestDto);

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


    @AuthCheck(role = AuthCheck.Role.USER)
    @PutMapping("/post")
    public ResponseEntity<?> updatePost(@RequestBody @Valid PostUpdateDto updateDto) throws Exception {

        postService.updatePost(UserContext.userId.get(), updateDto);
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK
        ));
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) throws ValidationException {
        postService.deletePost(UserContext.userId.get(), postId);
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK
        ));
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @GetMapping("/userPostList")
    public ResponseEntity<?> getUserPostList(@RequestParam int currentPage) {
        return ResponseEntity.ok().body(
                postService.getUserPostList(UserContext.userId.get(), currentPage)
                        .map(UserPostResponseDto::new));
    }

}
