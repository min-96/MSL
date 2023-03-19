package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.repository.PostRepository;
import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.service.ReportService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static Maswillaeng.MSLback.common.message.SuccessMessage.*;

@RequiredArgsConstructor
@RestController
public class ReportController {

    private final ReportService reportService;
    private final PostRepository postRepository;

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/api/report/{postId}")
    public ResponseEntity<?> saveReport(@PathVariable Long postId) {

        reportService.saveReport(postId);

        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_SAVE_REPORT));
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @DeleteMapping("/api/report/{postId}")
    public ResponseEntity<?> deleteReport(@PathVariable Long postId) {

        reportService.deleteReport(postId);

        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_DELETE_REPORT));
    }


    @ValidToken
    @AuthCheck(role = AuthCheck.Role.ADMIN)
    @DeleteMapping("/api/report/posts")
    public ResponseEntity<?> deleteReportPosts(@RequestBody List<Post> postList){

        postRepository.deleteAllById(postList);

        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_DELETE_REPORTED_POST_LIST));
    }

}
