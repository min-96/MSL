package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.common.exception.EntityNotFoundException;
import Maswillaeng.MSLback.common.exception.JwtNotValidException;
import Maswillaeng.MSLback.domain.entity.ChatRoom;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.reponse.UserApiResponse;
import Maswillaeng.MSLback.dto.user.reponse.UserInfoResponseDto;
import Maswillaeng.MSLback.dto.user.request.UserPwdResetRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import Maswillaeng.MSLback.utils.auth.AESEncryption;
import Maswillaeng.MSLback.utils.auth.UserContext;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final ChatService chatService;
    private final PostService postService;
    private final AESEncryption aesEncryption;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public UserInfoResponseDto getUser(Long targetId) {
        User followerListCnt = userRepository.findJoinFollowerById(targetId);
        User followingListCnt = userRepository.findJoinFollowingById(targetId);
        User UserAndPostCnt = userRepository.findByPostList(targetId);

        if (UserContext.userData.get() == null) {
            return new UserInfoResponseDto(followerListCnt.getFollowerList().size(), followingListCnt.getFollowingList().size(), UserAndPostCnt);
        } else {
            ChatRoom existChatRoom = chatService.getChatRoom(UserContext.userData.get().getUserId(), targetId);
            boolean isFollowed =
                    1 == followingListCnt.getFollowingList().stream().filter(follower -> follower.getFollower().getId().equals(UserContext.userData.get().getUserId())).toList().size();
            return new UserInfoResponseDto(followerListCnt.getFollowerList().size(), followingListCnt.getFollowingList().size(), UserAndPostCnt, isFollowed, existChatRoom);
        }
    }

    public void updateUser(Long userId, UserUpdateRequestDto requestDto) {
        User selectedUser = userRepository.findById(userId).get();
        selectedUser.update(requestDto);
    }

    public void userWithdraw(Long userId) {
        User findUser = userRepository.findById(userId).get();
        findUser.withdraw();
    }

    public UserApiResponse getUserApi(Long userId) {
        User user = userRepository.findById(userId).get();
        return new UserApiResponse(user, true);
    }

    public Map<String, String> uploadUserImage(MultipartFile imageFile, Long userId) throws IOException {
        Map<String, String> image = postService.uploadImage(imageFile);
        User user = userRepository.findById(userId).get();
        user.setUserImage(image.get("img"));

        return image;
    }

    public void resetPassword(UserPwdResetRequestDto requestDto) {

        try {
            jwtTokenProvider.getClaims(requestDto.getToken());
        } catch (JwtException e) {
            throw new JwtNotValidException();
        }

        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new EntityNotFoundException(User.class.getSimpleName()));
        String encryptedPassword = aesEncryption.encrypt(requestDto.getPassword());
        user.resetPassword(encryptedPassword);
    }
}
