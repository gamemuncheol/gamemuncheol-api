package com.gamemoonchul.infrastructure.web.dto.request;


import jakarta.validation.constraints.Size;

public record RegisterRequest(
        String temporaryKey,
        boolean privacyAgree,
        @Size(max = 10) String nickname
) {
}
