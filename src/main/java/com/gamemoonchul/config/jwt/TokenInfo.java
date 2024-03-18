package com.gamemoonchul.config.jwt;

import com.gamemoonchul.config.oauth.user.OAuth2Provider;

import java.util.Date;

public record TokenInfo(
        String email,
        String provider,
        String identifier,
        Date iat,
        Date exp
) { }
