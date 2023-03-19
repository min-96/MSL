package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.service.HashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static Maswillaeng.MSLback.common.message.SuccessMessage.*;

@RequiredArgsConstructor
@RestController
public class HashTagController {

    private final HashTagService hashTagService;

    @GetMapping("/api/best-tag")
    public ResponseEntity<?> getBestHashTagName() {
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_GET_BEST_HASHTAG,
                hashTagService.bestHashTag()));
    }

}
