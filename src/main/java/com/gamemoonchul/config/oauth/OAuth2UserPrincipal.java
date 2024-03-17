package com.gamemoonchul.config.oauth;

import com.gamemoonchul.config.oauth.user.OAuth2UserInfo;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Oauth 2.0 인증을 통해 얻은 사용자 정보를 Spring Security에서 사용할 수 있도록 Wrapping하는 역할을 함
 * isAccountNonExpired(), isAccountNonLocked(), isCredentialsNonExpired(), isEnabled(): UserDetails 인터페이스의 메서드들로, 계정의 상태를 나타내는 불리언 값을 반환
 * getAttributes(): OAuth2User 인터페이스의 메서드로, OAuth2User 인터페이스는 OAuth2 인증을 통해 얻은 사용자 정보를 담고 있음
 */

public class OAuth2UserPrincipal implements OAuth2User, UserDetails {

    private final OAuth2UserInfo userInfo;

    public OAuth2UserPrincipal(OAuth2UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userInfo.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return userInfo.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getName() {
        return userInfo.getEmail();
    }

    public OAuth2UserInfo getUserInfo() {
        return userInfo;
    }
}
