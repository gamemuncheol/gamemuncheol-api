package com.gamemoonchul.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Issuer {
    GOOGLE("google"),
    APPLE("apple");
    private String issuer;
}
