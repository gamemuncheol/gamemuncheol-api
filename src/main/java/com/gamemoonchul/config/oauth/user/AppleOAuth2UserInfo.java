package com.gamemoonchul.config.oauth.user;

import com.gamemoonchul.config.apple.AppleCredential;
import lombok.Getter;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
public class AppleOAuth2UserInfo implements OAuth2UserInfo {
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

    public AppleOAuth2UserInfo(OidcUser oidcUser) {
        Map<String, Object> attributes = oidcUser.getAttributes();
        this.accessToken = oidcUser.getIdToken()
                .getTokenValue();
        this.attributes = attributes;
        this.identifier = (String) attributes.get("sub"); // 없으면 에러 발생하는게 맞음
        this.email = (String) attributes.get("email"); // 없으면 에러 발생하는게 맞음
        this.birth = null;
        this.name = (String) attributes.getOrDefault("name", UUID.randomUUID()
                .toString()
                .substring(0, 8));
        this.firstName = null;
        this.lastName = null;
        this.nickname = (String) attributes.getOrDefault("name", UUID.randomUUID()
                .toString()
                .substring(0, 8));
        this.provider = OAuth2Provider.APPLE;
        this.profileImageUrl = null;
    }

    public AppleOAuth2UserInfo(AppleCredential credential) {
        this.accessToken = null;
        this.attributes = null;
        this.identifier = credential.getSub();
        this.email = credential.getEmail();
        this.birth = null;
        this.name = credential.getName();
        this.firstName = null;
        this.lastName = null;
        this.nickname = null;
        this.provider = OAuth2Provider.APPLE;
        this.profileImageUrl = null;
    }
}
