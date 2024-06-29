package com.gamemoonchul.infrastructure.web.dto.request;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;

public record RegisterRequest(
        String temporaryKey,
        boolean privacyAgree,
        @NotEmpty @Max(10) String nickname
) {
}
