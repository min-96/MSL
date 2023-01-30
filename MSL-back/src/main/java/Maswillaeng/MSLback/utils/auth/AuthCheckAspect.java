package Maswillaeng.MSLback.utils.auth;


import Maswillaeng.MSLback.common.exception.EntityNotFoundException;
import Maswillaeng.MSLback.domain.entity.User;
import Maswillaeng.MSLback.domain.repository.UserRepository;
import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Slf4j
@RequiredArgsConstructor
@Component
public class AuthCheckAspect {

    private static final String AUTHORIZATION = "Authorization";

    private final HttpServletRequest httpServletRequest;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Around("@annotation(Maswillaeng.MSLback.utils.auth.AuthCheck)")
    public Object authCheck(ProceedingJoinPoint pjp) throws Throwable {

        String token = httpServletRequest.getHeader(AUTHORIZATION);

        Long userId = jwtTokenProvider.getUserId(token);
        log.info("AuthCheck(userId) : " + userId);

        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("해당하는 유저를 찾을 수 없습니다.")
        );

        if (user.getWithdrawYn() == 1) {
            throw new EntityNotFoundException("탈퇴한 회원입니다.");
        }

        UserContext.currentMember.set(user);
        return pjp.proceed();
    }


}
