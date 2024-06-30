package com.gamemoonchul.infrastructure.web.dto;


import org.hibernate.validator.constraints.Length;

public record RegisterRequest(
        String temporaryKey,
        boolean privacyAgree,
        @Length(max = 10) String nickname
) {
}
