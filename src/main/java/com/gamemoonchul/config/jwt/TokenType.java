package com.gamemoonchul.config.jwt;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TokenType {
    ACCESS("access"),
    REFRESH("refresh");
    private final String type;
}
