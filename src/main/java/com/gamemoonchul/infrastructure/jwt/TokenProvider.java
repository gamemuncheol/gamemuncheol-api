package com.gamemoonchul.infrastructure.jwt;

import com.gamemoonchul.common.exception.ApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenProvider {

    private static final long ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS = 1000 * 60 * 120; // 2h
    private static final long REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS = 1000 * 60 * 24 * 7; // 7d

    @Value("${jwt.secret}")
    private String secret;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] key = Decoders.BASE64URL.decode(secret);
        this.key = Keys.hmacShaKeyFor(key);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (UnsupportedJwtException | MalformedJwtException exception) {
            throw new ApiException(JwtStatus.NOT_VALID_TOKEN);
        } catch (SignatureException exception) {
            throw new ApiException(JwtStatus.SIGNATURE_NOT_MATCH);
        } catch (ExpiredJwtException exception) {
            throw new ApiException(JwtStatus.EXPIRED_TOKEN);
        } catch (IllegalArgumentException exception) {
            throw new ApiException(JwtStatus.NOT_VALID_TOKEN);
        } catch (Exception exception) {
            throw new ApiException(JwtStatus.NOT_VALID_TOKEN);
        }
    }

    public String createToken(Authentication authentication, TokenType tokenType) {
        Date date = new Date();
        Date expiryDate = new Date();
        if (tokenType == TokenType.REFRESH_TOKEN) {
            expiryDate = new Date(date.getTime() + REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS);
        } else {
            expiryDate = new Date(date.getTime() + ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS);
        }

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(date)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public enum TokenType {
        ACCESS_TOKEN, REFRESH_TOKEN
    }

    /**
     * Jwt 토큰을 파싱해서 사용자 인증 정보 객체를 생성하는 과정을 담고 있음
     */
    public Authentication getAuthentication(String token) {
        // 토큰을 파싱해서 클레임을 뽑아냄
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        // Claim에서 User 정보를 뽑아냄
        UserDetails user = new User(claims.getSubject(), "", Collections.emptyList());

        // UserDetails를 이용해서 UsernamePasswordAuthenticationToken 객체를 생성해서 리턴
        // pricipal : 사용자의 세부 정보
        // credentials : 사용자의 비밀번호
        // authorities : 사용자의 권한 정보
        return new UsernamePasswordAuthenticationToken(user, "", Collections.emptyList());
    }
}
