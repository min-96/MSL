package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.service.ReportService;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.ValidToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReportController {

    private final ReportService reportService;

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @PostMapping("/api/report/{postId}")
    public ResponseEntity<?> saveReport(@PathVariable Long postId) {
        reportService.saveReport(postId);
        return ResponseEntity.ok().body(ResponseDto.of(
                "성공적으로 신고되었습니다."
        ));
    }

    @ValidToken
    @AuthCheck(role = AuthCheck.Role.USER)
    @DeleteMapping("/api/report/{postId}")
    public ResponseEntity<?> deleteReport(@PathVariable Long postId) {
        reportService.deleteReport(postId);
        return ResponseEntity.ok().body(ResponseDto.of(
                "신고가 취소되었습니다."
        ));
    }
}
