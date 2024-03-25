package com.gamemoonchul.config.jwt;

import com.gamemoonchul.config.oauth.user.OAuth2Provider;

public class TokenInfoDummy {
    public static TokenInfo createRefresh() {
        TokenInfo tokenInfo = TokenInfo.builder()
                .email("test@gmail.com")
                .identifier("test")
                .provider(OAuth2Provider.GOOGLE.toString())
                .tokenType(TokenType.REFRESH)
                .build();
        return tokenInfo;
    }

    public static TokenInfo createAccess() {
        TokenInfo tokenInfo = TokenInfo.builder()
                .email("test@gmail.com")
                .identifier("test")
                .provider(OAuth2Provider.GOOGLE.toString())
                .tokenType(TokenType.ACCESS)
                .build();
        return tokenInfo;
    }

}