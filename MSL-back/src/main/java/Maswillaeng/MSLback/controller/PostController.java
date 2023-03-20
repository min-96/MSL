package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.enums.Category;
import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.dto.post.request.PostRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostUpdateDto;
import Maswillaeng.MSLback.service.HashTagService;
import Maswillaeng.MSLback.service.PostService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.UserContext;
import Maswillaeng.MSLback.utils.auth.ValidToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

import static Maswillaeng.MSLback.common.message.SuccessMessage.*;

@RequiredArgsConstructor
@RestController
@Slf4j
public class PostController {

    private final PostService postService;

    @ValidToken
    @PostMapping("/api/change-format-image")
    public ResponseEntity<?> changeImageFormat(@RequestParam("photo") MultipartFile imageFile) throws IOException {
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_CHANGE_IMAGE_FORMAT,
                postService.uploadImage(imageFile)));
    }


    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/api/post")
    public ResponseEntity<?> savePost(@RequestBody @Valid PostRequestDto requestDto) {

        postService.registerPost(UserContext.userData.get().getUserId(), requestDto);

        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_SAVE_POST));
    }

    @GetMapping("/api/post")
    public ResponseEntity<?> getPostList(@RequestParam(required = false) Category category) {
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_GET_POST_LIST,
                postService.getPostList(category)));
    }

    @ValidToken
    @GetMapping("/api/post/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {

        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_GET_POST,
                postService.getPostById(postId)));
    }


    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PutMapping("/api/post")
    public ResponseEntity<?> updatePost(@RequestBody @Valid PostUpdateDto updateDto) {

        postService.updatePost(UserContext.userData.get().getUserId(), updateDto);

        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_UPDATE_POST));
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @DeleteMapping("/api/post/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {

        postService.deletePost(UserContext.userData.get().getUserId(), postId);

        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_DELETE_POST));
    }

    @GetMapping("/api/post/user")
    public ResponseEntity<?> getUserPostList(@RequestParam Long userId,
                                             @RequestParam(required = false) String category,
                                             @RequestParam int page) {
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_GET_USER_POST_LIST,
                postService.getUserPostList(userId, category, page))
        );
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.ADMIN)
    @GetMapping("/api/post/report")
    public ResponseEntity<?> getReportedPostList(@RequestParam int page) {
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_GET_REPORTED_POST_LIST,
                postService.getReportedPostList(page)));
    }

}
