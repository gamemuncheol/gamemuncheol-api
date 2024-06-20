package com.gamemoonchul.infrastructure.web.dto;

public record RegisterRequest(String temporaryKey, boolean privacyAgree, String nickname) {
}
