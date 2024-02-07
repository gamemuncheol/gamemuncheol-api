package com.gamemoonchul.config.jwt;

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
public class TokenHelper {

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

  public TokenDto createToken(Authentication authentication) {
    TokenDto tokenDto = TokenDto.builder()
        .accessToken(
            Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(
                    new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS)
                )
                .signWith(key, SignatureAlgorithm.HS512)
                .compact()
        )
        .refreshToken(
            Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(
                    new Date(new Date().getTime() + REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS)
                )
                .signWith(key, SignatureAlgorithm.HS512)
                .compact()
        )
        .build();
    return tokenDto;
  }

  public TokenDto createToken(String email) {
    TokenDto tokenDto = TokenDto.builder()
        .accessToken(
            Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                    new Date(new Date().getTime() + ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS)
                )
                .signWith(key, SignatureAlgorithm.HS512)
                .compact()
        )
        .refreshToken(
            Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                    new Date(new Date().getTime() + REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS)
                )
                .signWith(key, SignatureAlgorithm.HS512)
                .compact()
        )
        .build();
    return tokenDto;
  }

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
