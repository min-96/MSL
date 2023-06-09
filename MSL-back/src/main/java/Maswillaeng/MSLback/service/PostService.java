package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.common.exception.EntityNotFoundException;
import Maswillaeng.MSLback.domain.entity.*;
import Maswillaeng.MSLback.domain.enums.Category;
import Maswillaeng.MSLback.domain.repository.*;
import Maswillaeng.MSLback.dto.post.reponse.PostDetailResponseDto;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import Maswillaeng.MSLback.dto.post.request.PostRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostUpdateDto;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostQueryRepository postQueryRepository;
    private final HashTagService hashTagService;
///////////////////////////////
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    private final CommentLikeRepository commentLikeRepository;

    public void registerPost(Long userId, PostRequestDto postRequestDto) {
        User user = userRepository.findById(userId).get();
   //     String thumbnail = getThumbnail(postRequestDto.getContent());
        Post post = postRequestDto.toEntity(user);

        List<HashTag> resultHashTagList =hashTagService.insertHashTagList(postRequestDto.getHashTagList(), post);

        post.setHashTagList(resultHashTagList);
       
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostList(Category category) {
       // return postRepository.findAll();
        return postQueryRepository.findAllPostByCategory(category);
    }


    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostListsss() {
        return postRepository.findBySubList();
       // postRepository.findByLike(posts.)
      // return new PostResponseDto(posts);

    }

    public PostDetailResponseDto getPostById(Long postId) {
        Post post = postQueryRepository.findByIdFetchJoin(postId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다."));
        post.increaseHits();

        if (UserContext.userData.get() == null) {
            return new PostDetailResponseDto(post);
        } else {
            Long userId = UserContext.userData.get().getUserId();
            return new PostDetailResponseDto(post, userId);
        }

    }


    public void updatePost(Long userId, PostUpdateDto updateDto) throws Exception {
        Post selectedPost = postRepository.findById(updateDto.getPostId()).get();

        if (!Objects.equals(selectedPost.getUser().getId(), userId)) {
            throw new AccessDeniedException("접근 권한 없음");
        }

        List<String> updateHashTagList = updateDto.getHashTagList();
       // List<Tag> updateTagList = tagRepository.findByNameList(updateHashTagList);
     List<HashTag> resultHashTagList =   hashTagService.updateHashTagList(updateHashTagList,selectedPost);

//
        selectedPost.setHashTagList(resultHashTagList);
       // String thumbnail = getThumbnail(updateDto.getContent());
        selectedPost.update(updateDto);

    }

    public void deletePost(Long userId, Long postId) throws AccessDeniedException {
        Post post = postRepository.findById(postId).get();
        if (!Objects.equals(userId, post.getUser().getId())) {
            throw new AccessDeniedException("접근 권한 없음");
        }

          List<String> deleteHashTag =  post.getHashTagList().stream().map(h->h.getTag().getName()).collect(Collectors.toCollection(ArrayList::new));
       hashTagService.deleteHashTagList(deleteHashTag,post);
      //  postRepository.delete(post);
        post.disablePost();


    }


    @Transactional(readOnly = true)
    public Page<PostResponseDto> getUserPostList(Long userId, String category, int page) {

        return postQueryRepository.findAllPostByUserIdAndCategory(userId, category,
                PageRequest.of(page - 1, 20));
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getReportedPostList(int page) {
        return postQueryRepository.findByReportCount(PageRequest.of(page - 1, 20));
    }

    public Map<String,String> uploadImage(MultipartFile imageFile) throws IOException {
        byte[] imageData = imageFile.getBytes();
        UUID uuid = UUID.randomUUID();
        String uploadDir = "MSL-back/src/main/upload/img/";
        String savedFileName = uuid.toString() + "_" + imageFile.getOriginalFilename();
        Path path = Paths.get(uploadDir,savedFileName);

        Files.write(path, imageData);

        Map<String,String> imagePath = new HashMap<>();
        imagePath.put("img","/upload_img/"+savedFileName);

        return imagePath;
    }
}
