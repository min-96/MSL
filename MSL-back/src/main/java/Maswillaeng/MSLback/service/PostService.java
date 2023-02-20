package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.HashTag;
import Maswillaeng.MSLback.domain.entity.Post;
import Maswillaeng.MSLback.domain.entity.Tag;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.enums.Category;
import Maswillaeng.MSLback.domain.repository.*;
import Maswillaeng.MSLback.dto.post.reponse.PostDetailResponseDto;
import Maswillaeng.MSLback.dto.post.reponse.PostResponseDto;
import Maswillaeng.MSLback.dto.post.request.PostRequestDto;
import Maswillaeng.MSLback.dto.post.request.PostUpdateDto;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostQueryRepository postQueryRepository;
    private final TagRepository tagRepository;
    private final HashTagRepository hashTagRepository;

    private final HashTagService hashTagService;

    public void registerPost(Long userId, PostRequestDto postRequestDto) {
        User user = userRepository.findById(userId).get();
        Post post = postRequestDto.toEntity(user);

        List<HashTag> resultHashTagList =hashTagService.insertHashTagList(postRequestDto.getHashTagList(), post);

        post.setHashTagList(resultHashTagList);

        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostList(Category category) {

        return postQueryRepository.findAllPostByCategory(category);
    }

    @Transactional(readOnly = true)
    public PostDetailResponseDto getPostById(Long postId) {
        Post post = postQueryRepository.findByIdFetchJoin(postId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 게시물입니다."));

        if (UserContext.userData.get() == null) {
            return new PostDetailResponseDto(post);
        } else {
            Long userId = UserContext.userData.get().getUserId();
            return new PostDetailResponseDto(post, userId);
        }
    }

//    public List<HashTag> insertHashTagList(List<String> hashTagList, Post post) {
//
//        List<Tag> existHashTagList = tagRepository.findByNameList(hashTagList);
//
//        return hashTagList.stream()
//                .map(tagName -> existHashTagList.stream()
//                        .filter(t -> t.getName().equals(tagName))
//                        .findFirst()
//                        .map(tag -> new HashTag(tag, post))
//                        .orElseGet(() -> new HashTag(new Tag(tagName), post)))
//                .toList();
//    }


    public void updatePost(Long userId, PostUpdateDto updateDto,Long postId) throws Exception {
        Post selectedPost = postRepository.findById(postId).get();

        if (!Objects.equals(selectedPost.getUser().getId(), userId)) {
            throw new Exception("접근 권한 없음");
        }

        List<String> updateHashTagList = updateDto.getHashTagList();
       // List<Tag> updateTagList = tagRepository.findByNameList(updateHashTagList);
     List<HashTag> resultHashTagList =   hashTagService.updateHashTagList(updateHashTagList,selectedPost);

//        List<HashTag> oldHashTagList = hashTagRepository.findByPost(selectedPost);
//        List<String> oldStringTagList = oldHashTagList.stream().map(h -> h.getTag().getName()).collect(Collectors.toCollection(ArrayList::new));
//
//        List<String> removeHashTag = oldStringTagList.stream()
//                .filter(old -> updateHashTagList.stream().noneMatch(Predicate.isEqual(old)))
//                .collect(Collectors.toList());
//
//        List<String> insertHashTag = updateHashTagList.stream()
//                .filter(update -> oldStringTagList.stream().noneMatch(Predicate.isEqual(update)))
//                .collect(Collectors.toList());

     //   if(removeHashTag.isEmpty())

//        for(String r : removeHashTag) {
//            List<HashTag> removeHashTagList = hashTagRepository.findByNames(r);
//            if(!(removeHashTagList.size() >1)){
//               for(HashTag h : removeHashTagList) {
//                   hashTagRepository.deleteById(h.getId());
//                   tagRepository.deleteById(h.getTag().getName());
//               }
//            }
//        }

 //    List<HashTag>  resultHashTagList =  insertHashTagList(insertHashTag ,selectedPost);

//        for(Object[] r : removeHashTagList){
//          if(r[1].equals(1)){
//              System.out.println(r[0]);
//          }
//        }

  //     hashTagRepository.deleteByTagName(removeHashTagList);

     //   removeHashTagList.stream().forEach(t->hashTagRepository.deleteByHashTag(t[0].toString(),postId));
  //      removeHashTagList.stream().filter(f->((Number)f[1]).intValue()<=(1)).forEach(t->{tagRepository.deleteById(t[0].toString());});
   //     removeHashTagList.stream().forEach(t->hashTagRepository.deleteByHashTag(t[0].toString(),postId));
      //  removeHashTagList.stream().forEach(t->hashTagRepository.deleteByHashTag(t[0].toString(),postId));
     //   tagRepository.delete(new Tag())

 //       removeHashTagList.stream().filter(f->((Number)f[1]).intValue()<=1).forEach(t->HashTagRepository.deleteByHashTag(new HashTag(new Tag(t[0].toString(),selectedPost))));
//        removeHashTagList.stream()
//                        .filter(f->{
//                            String tagName =(String)f[0];
//                            Integer count = ((Number)f[1]).intValue();
//                            return count.equals(1);
//                        }).map();
             //   스트림으로 써서 size 0 인거 찾아서name 가져와서 tagRepository에서 지우기.


    //  removeHashTag.stream().map(tagName->removeHashTagCount.stream().filter(t.)


      // Long count = hashTagRepository.countByName(removeHashTag);
       // System.out.println(count);
//        if(count<=0){
//            tagRepository.deleteById(removeHashTag.toString());
//        }else{
//
//        }


        //List<Tag> oldTagList = oldHashTagList.stream().map(HashTag::getTag).toList();

//        oldHashTagList.stream()
//                .filter(oldHashTag -> !updateHashTagList.contains(oldHashTag.getTag().getName()))
//                .forEach(hashTagRepository::delete);
//
//        List<HashTag> newHashTagList = updateTagList.stream()
//                .filter(tag -> !oldTagList.contains(tag))
//                .map(tag -> new HashTag(tag, selectedPost))
//                .toList();
//
//        List<HashTag> keepHashTagList = hashTagRepository.findByPostAndTagIn(selectedPost, updateTagList);
//
//        List<HashTag> resultHashTagList = new ArrayList<>();
//
//        resultHashTagList.addAll(keepHashTagList);
//        resultHashTagList.addAll(newHashTagList);
//
        selectedPost.setHashTagList(resultHashTagList);
        selectedPost.update(updateDto);

    }

    public void deletePost(Long userId, Long postId) throws ValidationException {
        Post post = postRepository.findById(postId).get();
        if (!Objects.equals(userId, post.getUser().getId())) {
            throw new ValidationException("접근 권한 없음");
        }

          List<String> deleteHashTag =  post.getHashTagList().stream().map(h->h.getTag().getName()).collect(Collectors.toCollection(ArrayList::new));
        hashTagService.deleteHashTagList(deleteHashTag);
     //   hashTagRepository.deleteByPostId(post.getId());
        postRepository.delete(post);
       // tagRepository.deleteByIds(deleteHashTag);

    }

    public Page<Post> getUserPostList(Long userId, int currentPage) {
        return postRepository.findByUserIdFetchJoin(userId, PageRequest.of(
        currentPage - 1, 20, Sort.Direction.DESC, "createdAt"));
    }
}
