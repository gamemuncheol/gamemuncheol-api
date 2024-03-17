package com.gamemoonchul.config.oauth.user;

import java.time.LocalDateTime;
import java.util.Map;

public interface OAuth2UserInfo {

    OAuth2Provider getProvider();

    String getAccessToken();

    Map<String, Object> getAttributes();

    String getIdentifier();

    String getEmail();

    LocalDateTime getBirth();

    String getName();

    String getFirstName();

    String getLastName();

    String getNickname();

    String getProfileImageUrl();
}
