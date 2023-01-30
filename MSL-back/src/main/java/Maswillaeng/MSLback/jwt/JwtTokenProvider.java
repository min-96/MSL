package Maswillaeng.MSLback.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {

    @Value("${secret.access}")
    private String SECRET_KEY;
    @Value("${secret.refresh}")
    private String REFRESH_KEY;

    private final long ACCESS_TOKEN_VALID_TIME = 1000 * 60 * 60; // 1시간
//    private final long ACCESS_TOKEN_VALID_TIME = 1; // 만료 테스트
    private final long REFRESH_TOKEN_VALID_TIME = 1000 * 60 * 60 * 24; // 24시간

    @Override
    public void afterPropertiesSet() throws Exception {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        REFRESH_KEY = Base64.getEncoder().encodeToString(REFRESH_KEY.getBytes());
    }

    public String createAccessToken(Long userId, String role) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("role", role);

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME);

        return Jwts.builder()
              .setClaims(claims)
                .setIssuedAt(now) // 토큰 생성 시간
                .setExpiration(expiredDate) // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String createRefreshToken(Long userId, String role) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("role", role); // 리프레쉬 토큰에 유저 정보가 필요한가?

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) // 토큰 생성 시간
                .setExpiration(expiredDate) // 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, REFRESH_KEY)
                .compact();
    }

    public Long getUserId(String token) {
        return Long.valueOf(String.valueOf(getAccessClaims(token).get("userId")));
    }

    public boolean isValidAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (NullPointerException e) {
            log.error("access token is null");
            return false;
        } catch (ExpiredJwtException e) {
            log.error("access token expired.");
            return false;
        } catch (JwtException e) {
            log.error("access token tampered");
            return false;
        }
    }

    public boolean isValidRefreshToken(String token) {
        try {
            Claims claims = this.getRefreshClaims(token);
            return true;
        } catch (JwtException e) {
            log.error("refresh token tampered");
            return false;
        } catch (NullPointerException e) {
            log.error("refresh token is null");
            return false;
        }
    }


    private Claims getAccessClaims(String token) {
        token = token.substring(7);
        isValidAccessToken(token);
        return Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token).getBody();
    }

    private Claims getRefreshClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(REFRESH_KEY)
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.error("refresh token expired");
            return e.getClaims();
        }
    }
}
