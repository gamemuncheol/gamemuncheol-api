package com.gamemoonchul.config.oauth.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuth2Provider {
    GOOGLE("google"),
    APPLE("apple")
    ;

    private final String registrationId;
}
