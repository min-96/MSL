package Maswillaeng.MSLback.service;

import Maswillaeng.MSLback.common.exception.*;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.dto.user.reponse.LoginResponseDto;
import Maswillaeng.MSLback.dto.user.reponse.TokenResponseDto;
import Maswillaeng.MSLback.dto.user.request.LoginRequestDto;
import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import Maswillaeng.MSLback.utils.auth.AESEncryption;
import Maswillaeng.MSLback.utils.auth.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static Maswillaeng.MSLback.jwt.JwtTokenProvider.REFRESH_TOKEN_VALID_TIME;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AESEncryption aesEncryption;

    public void sign(User user) {
        String encryptPwd = aesEncryption.encrypt(user.getPassword());
        user.resetPassword(encryptPwd);
        userRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(User.class.getSimpleName()));
        String encryptedPassword = aesEncryption.encrypt(requestDto.getPassword());

        if (!user.getPassword().equals(encryptedPassword)) {
            throw new PasswordNotMatchedException();
        } else if (user.getWithdrawYn() == 1) {
            throw new UserAlreadyWithdrewException();
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());
        user.updateRefreshToken(refreshToken);
        TokenResponseDto token = TokenResponseDto.builder().
                ACCESS_TOKEN(accessToken).REFRESH_TOKEN(refreshToken)
                .build();
        return LoginResponseDto.builder()
                .tokenResponseDto(token)
                .nickName(user.getNickName())
                .userImage(user.getUserImage())
                .build();
    }

    public TokenResponseDto updateAccessToken(String refresh_token){
        String updateAccessToken;

           User user = userRepository.findById(UserContext.userData.get().getUserId()).get();
           String OriginalRefreshToken = user.getRefreshToken();
           if (OriginalRefreshToken.equals(refresh_token)) {
               updateAccessToken = jwtTokenProvider.createAccessToken(user.getId(), user.getRole());
           } else {
               user.destroyRefreshToken();
               userRepository.save(user);
               throw new JwtTamperedException();
           }

        return TokenResponseDto.builder()
                .ACCESS_TOKEN(updateAccessToken)
                .build();
    }

    public ResponseCookie getAccessTokenCookie(String accessToken){
        return ResponseCookie.from(
                        "ACCESS_TOKEN", accessToken)
                .path("/")
                .httpOnly(true)
                .maxAge(REFRESH_TOKEN_VALID_TIME)
                .sameSite("Lax")
                .build();
    }

    public ResponseCookie getRefreshTokenCookie(String refreshToken){
        return ResponseCookie.from(
                        "REFRESH_TOKEN", refreshToken)
                .path("/api/update-token")
                .httpOnly(true)
                .maxAge(REFRESH_TOKEN_VALID_TIME)
                .sameSite("Lax")
                .build();
    }

    public void checkDuplicateUser(User user) {
        if (userRepository.existsByNickName(user.getNickName()) ||
                userRepository.existsByEmail(user.getEmail())) {
            throw new AlreadyExistException(User.class.getSimpleName());
        }
    }

    public void removeRefreshToken(Long userId) {
        User user = userRepository.findById(userId).get();
        user.destroyRefreshToken();
    }
}
