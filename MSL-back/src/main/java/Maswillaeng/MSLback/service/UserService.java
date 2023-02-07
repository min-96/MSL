package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.reponse.LoginResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.TokenResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.UserInfoResponseDto;
import Maswillaeng.MSLback.dto.user.request.LoginRequestDto;
import Maswillaeng.MSLback.dto.user.request.UserUpdateRequestDto;
import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;


    public void join(User user) {
        userRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
        if (!user.getPassword().equals(requestDto.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        } else if (user.getWithdrawYn() == 1) {
            throw new EntityNotFoundException("탈퇴한 회원입니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken();
        user.updateRefreshToken(refreshToken);
        userRepository.save(user);
        TokenResponseDto token = TokenResponseDto.builder().
                ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken)
                .build();
        return LoginResponseDto.builder()
                .tokenResponseDto(token)
                .nickName(user.getNickName())
                .userImage(user.getUserImage())
                .build();
    }

    // TODO : 추후수정
    public TokenResponseDto updateAccessToken(String access_token, String refresh_token) throws Exception {
        String updateAccessToken;
        if(access_token!=null){
            // Claims claimsToken =  jwtTokenProvider.getRefreshClaims(refresh_token);
            Long userId = jwtTokenProvider.getUserId(access_token);
            User user = userRepository.findById(userId).get();
            String OriginalRefreshToken = user.getRefreshToken();
            if(OriginalRefreshToken.equals(refresh_token)){
                updateAccessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getRole());
            }else{
                user.destroyRefreshToken();
                userRepository.save(user);
                throw new Exception("변조된 토큰");
            }
        }else
            throw new Exception("access Token이 없습니다");

        return TokenResponseDto.builder()
                .ACCESS_TOKEN(updateAccessToken)
                .REFRESH_TOKEN(refresh_token)
                .build();
    }

    @Transactional(readOnly = true)
    public UserInfoResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId).get();
        return UserInfoResponseDto.of(user);

    }

    public void updateUser(Long userId, UserUpdateRequestDto requestDto) {
        User selectedUser = userRepository.findById(userId).get();

        selectedUser.update(requestDto); // 더티체킹
//        userRepository.save(selectedUser);
    }

    public void userWithdraw(Long userId) {
        User findUser = userRepository.findById(userId).get();
        findUser.withdraw();
    }

    public boolean joinDuplicate(User user) {
        return userRepository.existsByNickName(user.getNickName()) ||
                userRepository.existsByEmail(user.getEmail());
    }
}
