package com.gamemoonchul.config.jwt;

import com.gamemoonchul.domain.enums.MemberRole;

public class TokenInfoDummy {
    public static TokenInfo createRefresh() {
        TokenInfo tokenInfo = TokenInfo.builder()
                .email("test@gmail.com")
                .id(1L)
                .tokenType(TokenType.REFRESH)
                .role(MemberRole.USER)
                .build();
        return tokenInfo;
    }

    public static TokenInfo createAccess() {
        TokenInfo tokenInfo = TokenInfo.builder()
                .email("test@gmail.com")
                .id(1L)
                .tokenType(TokenType.ACCESS)
                .role(MemberRole.USER)
                .build();
        return tokenInfo;
    }

}