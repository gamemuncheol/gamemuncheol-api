package com.gamemoonchul.domain.entity;

import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.domain.enums.MemberRole;

public class MemberDummy {
    public static Member create() {
        return Member.builder()
                .provider(OAuth2Provider.GOOGLE)
                .email("test@gmail.com")
                .nickname("적길동")
                .identifier("test")
                .name("적길동")
                .picture("https://www.naver.com")
                .score(0.0)
                .role(MemberRole.USER)
                .build();
    }
}