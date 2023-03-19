package Maswillaeng.MSLback.controller;

import Maswillaeng.MSLback.dto.common.ResponseDto;
import Maswillaeng.MSLback.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static Maswillaeng.MSLback.common.message.SuccessMessage.*;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("api/search")
    public ResponseEntity<?> search(@RequestParam String keyword, @RequestParam int page) {
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_GET_SEARCH_RESULT,
                searchService.searchByKeyword(keyword, page)));
    }

    @GetMapping("/api/search/tag")
    public ResponseEntity<?> getPostListByHashTag(@RequestParam String name, @RequestParam int page) {
        return ResponseEntity.ok().body(ResponseDto.of(
                SUCCESS_GET_HASHTAG_SEARCH_RESULT,
                searchService.searchPostByHashTag(name, page)));
    }

}
