package com.gamemoonchul.infrastructure.web.dto;

import com.gamemoonchul.application.validation.ValidNickname;

public record RegisterRequest(String temporaryKey, boolean privacyAgree, @ValidNickname String nickname) {
}
