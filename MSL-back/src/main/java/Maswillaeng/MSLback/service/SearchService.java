package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.repository.PostQueryRepository;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor

public class SearchService {

    private final PostQueryRepository postQueryRepository;

    @Transactional(readOnly = true)
    public Page<PostResponseDto> searchByKeyword(String keyword, int page) {
        return postQueryRepository.findByKeyword(keyword, PageRequest.of(page - 1, 20));

    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> searchPostByHashTag(String tagName, int page){
        return postQueryRepository.findByHashTagNam(tagName, PageRequest.of(page-1,20));
    }
}
