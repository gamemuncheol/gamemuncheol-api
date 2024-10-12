package com.gamemoonchul.infrastructure.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record AppleSignUpRequestDto(
    String identityToken,
    String name
) {
}

