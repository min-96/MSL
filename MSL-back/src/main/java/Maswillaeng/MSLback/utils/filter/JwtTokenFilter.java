package Maswillaeng.MSLback.utils.filter;

import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("filter");
        String accessToken = new String();
        String refreshToken = new String();
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Test api중 h2-console 사용할때 자동으로 쿠키가 저장되어 /updateToken으로 넘어감
        String uri = req.getRequestURI();
        if(uri.contains("/h2-console")) {
            chain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = req.getCookies();
        if(cookies!=null) {
            log.info("accessToken = " + accessToken);
            for (Cookie cookie : cookies) {
                log.info("cookie.getName = " + cookie.getName());
                switch (cookie.getName()) {
                    case "ACCESS_TOKEN":
                        accessToken = cookie.getValue();
                        break;
                    case "REFRESH_TOKEN":
                        refreshToken = cookie.getValue();
                }
            }
        }

        if (!refreshToken.equals("")) {
            try {
                jwtTokenProvider.isValidToken(refreshToken);
            } catch (ExpiredJwtException exception) {
                res.sendRedirect("/login");
            }catch (JwtException exception) {
                System.out.println("Token Tampered");
                return;
            } catch (NullPointerException exception) {
                System.out.println("Token is null");
                return;
            }
        }else if(!accessToken.equals("")){
            try {
                jwtTokenProvider.isValidToken(accessToken);
            } catch (ExpiredJwtException exception) {
                res.sendRedirect("/updateToken");
            }catch (JwtException exception) {
                System.out.println("Token Tampered");
                return;
            } catch (NullPointerException exception) {
                System.out.println("Token is null");
                return;
            }
        }

        request.setAttribute("ACCESS_TOKEN" , accessToken);

        chain.doFilter(request, response);
    }
}
