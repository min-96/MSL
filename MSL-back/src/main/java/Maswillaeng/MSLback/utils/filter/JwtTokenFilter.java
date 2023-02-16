//package Maswillaeng.MSLback.utils.filter;
//
//import Maswillaeng.MSLback.jwt.JwtTokenProvider;
//import Maswillaeng.MSLback.utils.auth.TokenUserData;
//import Maswillaeng.MSLback.utils.auth.UserContext;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.JwtException;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class JwtTokenFilter implements Filter {
//
//    private final JwtTokenProvider jwtTokenProvider;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        String accessToken = new String();
//        String refreshToken = new String();
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//
//        String uri = req.getRequestURI();
//
//        System.out.println(uri);
//
//        if(uri.contains("/h2-console")) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        Cookie[] cookies = req.getCookies();
//        if(cookies!=null) { // 쿠키가 존재한다면
//            for (Cookie cookie : cookies) {
//                switch (cookie.getName()) {
//                    case "ACCESS_TOKEN":
//                        accessToken = cookie.getValue();
//                        break;
//                    case "REFRESH_TOKEN":
//                        refreshToken = cookie.getValue();
//                }
//            }
//        }
//
//        if (!refreshToken.equals("")) { // 리프레시 토큰이 있다면
//            try {
//                System.out.println("refresh Token");
//                Claims claims = jwtTokenProvider.getClaims(refreshToken);
//                UserContext.userData.set(new TokenUserData(claims));
//            } catch (ExpiredJwtException exception) {
//                res.sendRedirect("/login"); }
//            // TODO : 나중에 Exception Handler JwtException, NullPointerException 로 관리 (401)
//
//        }
//        else if(!accessToken.equals("")){
//            try {
//                Claims claims = jwtTokenProvider.getClaims(accessToken);
//                UserContext.userData.set(new TokenUserData(claims));
//            } catch (ExpiredJwtException exception) {
//              res.setHeader("Set-Cookie" , "FROM="+uri);
////             res.setHeader("Access-Control-Allow-Origin", "http://localhost:3000/");
////             res.setHeader("Access-Control-Allow-Methods","GET");
////             res.setHeader("Access-Control-Allow-Headers"," X-PINGOTHER, Content-Type");
//           //     res.setHeader("Access-Control-Allow-Origin" ,"*");
//              res.sendRedirect("/updateToken");
//              return;
//            }
//            // TODO : 나중에 Exception Handler JwtException, NullPointerException 로 관리
//
//        }
//
//        chain.doFilter(request, response);
//    }
//}
