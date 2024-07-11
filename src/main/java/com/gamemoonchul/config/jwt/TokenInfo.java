package com.gamemoonchul.config.jwt;

import com.gamemoonchul.domain.enums.MemberRole;
import lombok.Builder;

import java.util.Date;

@Builder
public record TokenInfo(
        String email,
        Long id,
        MemberRole role,
        TokenType tokenType,
        Date iat,
        Date exp
) {
}
