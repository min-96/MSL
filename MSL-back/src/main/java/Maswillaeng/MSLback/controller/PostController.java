package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.enums.Category;
import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import Maswillaeng.MSLback.dto.post.reponse.UserPostResponseDto;
import Maswillaeng.MSLback.dto.post.request.PostRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostUpdateDto;
import Maswillaeng.MSLback.service.PostService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/api/post")
    public ResponseEntity<?> savePost(@RequestBody @Valid PostRequestDto requestDto) {

        postService.registerPost(UserContext.userData.get().getUserId(), requestDto);

        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK
        ));
    }

    @GetMapping("/api/post/page")
    public ResponseEntity<?> getPostList(@RequestParam(required = false) Category category) {

        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK, postService.getPostList(category)));
    }

    @GetMapping("/api/post/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {

        Post post = postService.getPostById(postId);
        System.out.println("UserContext.userData.get().getUserId() = " + UserContext.userData.get().getUserId());
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK, null // 잠깐 임시로
        ));
    }


    @AuthCheck(role = AuthCheck.Role.USER)
    @PutMapping("/api/post")
    public ResponseEntity<?> updatePost(@RequestBody @Valid PostUpdateDto updateDto) throws Exception {

        postService.updatePost(UserContext.userData.get().getUserId(), updateDto);
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK
        ));
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @DeleteMapping("/api/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) throws ValidationException {
        postService.deletePost(UserContext.userData.get().getUserId(), postId);
        return ResponseEntity.ok().body(ResponseDto.of(
                HttpStatus.OK
        ));
    }

    @AuthCheck(role = AuthCheck.Role.USER)
    @GetMapping("/api/userPostList")
    public ResponseEntity<?> getUserPostList(@RequestParam int currentPage) {
        return ResponseEntity.ok().body(
                postService.getUserPostList(UserContext.userData.get().getUserId(), currentPage)
                        .map(UserPostResponseDto::new));
    }



}
