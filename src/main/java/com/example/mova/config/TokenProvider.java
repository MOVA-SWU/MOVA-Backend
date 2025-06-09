package com.example.mova.config;

import com.example.mova.dto.JwtToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider {

    private final Key key;
    private final long ACCESS_TOKEN_VALIDITY = 24 * 60 * 60 * 1000L;   // 1일
    private final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000L; // 7일

    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // User 정보를 가지고 AccessToken, RefreshToken 생성
    public JwtToken generateToken(Authentication authentication) {
        String username = authentication.getName();
        long now = System.currentTimeMillis();

        // 1) Access Token
        Date atExpiry = new Date(now + ACCESS_TOKEN_VALIDITY);
        String accessToken = Jwts.builder()
                .setSubject(username)
                .setExpiration(atExpiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // 2) Refresh Token (username 클레임 포함, 만료기간 분리)
        Date rtExpiry = new Date(now + REFRESH_TOKEN_VALIDITY);
        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setExpiration(rtExpiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // Refresh 엔드포인트에서 username 꺼낼 때 사용
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Authentication getAuthentication(String token) {
        // 토큰에서 subject(username) 꺼내기
        String username = getUsernameFromToken(token);
        // UserDetails 대신 간단히 Spring Security User객체 생성
        User principal = new User(username, "", Collections.emptyList());
        // UsernamePasswordAuthenticationToken의 두번째 인자는 credentials, 세번째는 권한 리스트
        return new UsernamePasswordAuthenticationToken(principal, token, Collections.emptyList());
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    // 토큰 클레임 정보 가져오기
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
