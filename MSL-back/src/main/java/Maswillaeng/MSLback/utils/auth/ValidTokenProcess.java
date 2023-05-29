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
        if (cookies != null) {
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

        if (!refreshToken.equals("")) {
            try {
                Claims claims = jwtTokenProvider.getClaims(refreshToken);
                UserContext.userData.set(new TokenUserData(claims));
            } catch (ExpiredJwtException exception) {
                res.sendRedirect("/api/login");
                return false;
            }


        } else if (!accessToken.equals("")) {
            try {
                Claims claims = jwtTokenProvider.getClaims(accessToken);
                UserContext.userData.set(new TokenUserData(claims));
            } catch (ExpiredJwtException exception) {
                res.setStatus(401);  // 에러 만들기
                return false;
            }

        }
        return true;
    }
}
