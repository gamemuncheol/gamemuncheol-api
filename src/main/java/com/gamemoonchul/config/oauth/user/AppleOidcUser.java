package com.gamemoonchul.config.oauth.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.Map;

@Getter
public class AppleOidcUser implements OidcUser {
    Map<String, Object> claims;
    OidcUserInfo userInfo;
    OidcIdToken idToken;
    Map<String, Object> attributes;
    Collection<? extends GrantedAuthority> authorities;
    String name;

    public AppleOidcUser() {
        this.claims = claims;
        this.userInfo = userInfo;
        this.idToken = idToken;
        this.attributes = attributes;
        this.authorities = authorities;
        this.name = name;
    }
}
