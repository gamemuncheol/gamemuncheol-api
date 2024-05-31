package com.gamemoonchul.config.oauth.user;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class GoogleOAuth2UserInfo implements OAuth2UserInfo {
    private final Map<String, Object> attributes;
    private final OAuth2Provider provider;
    private final String accessToken;
    /**
     * https://developers.google.com/assistant/identity/google-sign-in-oauth?hl=ko
     * 에 따르면 Google의 Unique ID는 sub로 제공된다.
     */
    private final String identifier;
    private final String email;
    private final LocalDateTime birth;
    private final String name;
    private final String firstName;
    private final String lastName;
    private final String nickname;
    private final String profileImageUrl;

    public GoogleOAuth2UserInfo(String accessToken, Map<String, Object> attributes) {
        this.accessToken = accessToken;
        this.attributes = attributes;
        this.identifier = (String) attributes.get("sub");
        this.email = (String) attributes.get("email");
        this.birth = attributes.get("birthdays") == null ? null : (LocalDateTime) attributes.get("birthdays");
        this.name = (String) attributes.get("name");
        this.firstName = (String) attributes.get("given_name");
        this.lastName = (String) attributes.get("family_name");
        this.nickname = (String) attributes.get("nickname");
        this.provider = OAuth2Provider.GOOGLE;
        this.profileImageUrl = (String) attributes.get("picture");
    }
}
