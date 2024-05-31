package com.gamemoonchul.config.jwt;

import lombok.Builder;

import java.util.Date;

@Builder
public record TokenInfo(
        String email,
        String provider,
        String identifier,
        TokenType tokenType,
        Date iat,
        Date exp
) {
}
