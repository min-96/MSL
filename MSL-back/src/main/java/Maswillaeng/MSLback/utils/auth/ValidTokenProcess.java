package Maswillaeng.MSLback.utils.auth;

import Maswillaeng.MSLback.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ValidTokenProcess {

    public static boolean execute(HttpServletRequest req, HttpServletResponse res,JwtTokenProvider jwtTokenProvider) throws IOException {
        String accessToken = new String();
        String refreshToken = new String();

        Cookie[] cookies = req.getCookies();
        if (cookies != null) { // 쿠키가 존재한다면
            for (Cookie cookie : cookies) {
                switch (cookie.getName()) {
                    case "ACCESS_TOKEN":
                        accessToken = cookie.getValue();
                        break;
                    case "REFRESH_TOKEN":
                        refreshToken = cookie.getValue();
                }
            }
        }

        if (!refreshToken.equals("")) { // 리프레시 토큰이 있다면
            try {
                Claims claims = jwtTokenProvider.getClaims(refreshToken);
                UserContext.userData.set(new TokenUserData(claims));
            } catch (ExpiredJwtException exception) {
                res.sendRedirect("/api/login");
                return false;
            }
            // TODO : 나중에 Exception Handler JwtException, NullPointerException 로 관리 (401)

        } else if (!accessToken.equals("")) {
            try {
                Claims claims = jwtTokenProvider.getClaims(accessToken);
                UserContext.userData.set(new TokenUserData(claims));
            } catch (ExpiredJwtException exception) {
                res.setStatus(401);
                return false;
            }
            // TODO : 나중에 Exception Handler JwtException, NullPointerException 로 관리
        }
        return true;
    }
}
