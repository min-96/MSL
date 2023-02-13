package Maswillaeng.MSLback.utils.interceptor;

import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import Maswillaeng.MSLback.utils.auth.AuthCheck;
import Maswillaeng.MSLback.utils.auth.TokenUserData;
import Maswillaeng.MSLback.utils.auth.UserContext;
import io.jsonwebtoken.Claims;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ComponentScan
@NoArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod;

        if (!(handler instanceof HandlerMethod))
            return true;
        handlerMethod = (HandlerMethod) handler;

        AuthCheck auth = handlerMethod.getMethodAnnotation(AuthCheck.class);
        if (auth == null) {
            System.out.println("auth = " + auth);
            return true;
        }


        String token = (String) request.getAttribute("ACCESS_TOKEN");

        // 어노테이션 붙은 페이지를 비회원이 접근하려함
        if (token.equals("")) {
            response.setStatus(401);
            return false;
        }

        TokenUserData userData = UserContext.userData.get();

        if (auth.role().equals(AuthCheck.Role.USER)) {
            // USER 어노테이션 붙은곳에 도착한 계정이 USER 권한을 가지고있는지 체크하는거
            if (!userData.getUserRole().equals(AuthCheck.Role.USER.toString())) {
                response.setStatus(401);
                return false;
            }
        }

        if (auth.role().equals(AuthCheck.Role.ADMIN)) {
            // ADMIN 어놑테이션 붙은 컨트롤러에 접근한 계정이 AMDIN인지 확인
            if (!userData.getUserRole().equals(AuthCheck.Role.ADMIN.toString())) {
                response.setStatus(401);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserContext.remove(); // 쓰레드 로컬 지워주기.
    }

}
