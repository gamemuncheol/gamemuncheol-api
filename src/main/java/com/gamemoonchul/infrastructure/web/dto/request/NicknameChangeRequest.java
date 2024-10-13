package com.gamemoonchul.infrastructure.web.dto.request;

import jakarta.validation.constraints.Size;

public record NicknameChangeRequest(
    @Size(max = 10)
    String nickname
) {
}
