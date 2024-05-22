package com.gamemoonchul.config.jwt;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.config.oauth.user.OAuth2UserInfo;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.status.JwtStatus;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class TokenHelper {

    private static final long ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS = 1000 * 60 * 120; // 2h
    private static final long REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS = 1000 * 60 * 24 * 7; // 7d

    @Value("${jwt.secret}")
    private String secret;

    private final MemberRepository memberRepository;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] key = Decoders.BASE64URL.decode(secret);
        this.key = Keys.hmacShaKeyFor(key);
    }

    public boolean validateToken(String token, TokenType type) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            TokenInfo tokenInfo = getTokenInfo(token);
            if(tokenInfo.tokenType() != type) {
                throw new ApiException(JwtStatus.TOKEN_TYPE_NOT_MATCH);
            }
            return true;
        } catch (SignatureException exception) {
            log.error(exception.getMessage() + "\n" + exception.getStackTrace().toString());
            throw new ApiException(JwtStatus.SIGNATURE_NOT_MATCH);
        } catch (ExpiredJwtException exception) {
            log.error(exception.getMessage() + "\n" + exception.getStackTrace().toString());
            throw new ApiException(JwtStatus.EXPIRED_TOKEN);
        } catch (Exception exception) {
            log.error(exception.getMessage() + "\n" + exception.getStackTrace().toString());
            throw new ApiException(JwtStatus.NOT_VALID_TOKEN);
        }
    }

    public TokenDto generateToken(OAuth2UserInfo oAuth2UserInfo) {
        Map<String, String> claims = Map.of(
                "email", oAuth2UserInfo.getEmail(),
                "identifier", oAuth2UserInfo.getIdentifier(),
                "provider", oAuth2UserInfo.getProvider().toString()
        );
        return createTokenDto(claims);
    }

    public TokenDto generateToken(TokenInfo tokenInfo) {
        Map<String, String> claims = Map.of(
                "email", tokenInfo.email(),
                "identifier", tokenInfo.identifier(),
                "provider", tokenInfo.provider()
        );
        return createTokenDto(claims);
    }

    private TokenDto createTokenDto(Map<String, String> claims) {
        Map<String, String> accessClaims = new HashMap<>(claims);
        Map<String, String> refreshClaims = new HashMap<>(claims);
        accessClaims.put("type", TokenType.ACCESS.toString());
        refreshClaims.put("type", TokenType.REFRESH.toString());

        return TokenDto.builder()
                .accessToken(createToken(accessClaims, ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS))
                .refreshToken(createToken(refreshClaims, REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS))
                .build();
    }

    private String createToken(Map<String, String> claims, long expirationTimeInMilliseconds) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationTimeInMilliseconds))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        // UserDetails를 이용해서 UsernamePasswordAuthenticationToken 객체를 생성해서 리턴
        // pricipal : 사용자의 세부 정보
        // credentials : 사용자의 비밀번호
        // authorities : 사용자의 권한 정보
        return new UsernamePasswordAuthenticationToken(getTokenInfo(token), "", getAuthorities(token));
    }

    private Collection<GrantedAuthority> getAuthorities(String token) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        TokenInfo tokenInfo = getTokenInfo(token);
        Optional<Member> member = memberRepository.findTop1ByProviderAndIdentifier(OAuth2Provider.valueOf(tokenInfo.provider()), tokenInfo.identifier());
        if (member.isPresent()) {
            authorities.add(new SimpleGrantedAuthority(member.get().getRole().getKey()));

        }
        return authorities;
    }

    public TokenInfo getTokenInfo(String token) {
        // 토큰을 파싱해서 클레임을 뽑아냄
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        TokenInfo tokenInfo = TokenInfo.builder().
                email(claims.get("email", String.class)).
                provider(claims.get("provider", String.class)).
                identifier(claims.get("identifier", String.class)).
                tokenType(TokenType.valueOf(claims.get("type", String.class)))
                .iat(claims.getIssuedAt())
                .exp(claims.getExpiration()).build();
        return tokenInfo;
    }
}
