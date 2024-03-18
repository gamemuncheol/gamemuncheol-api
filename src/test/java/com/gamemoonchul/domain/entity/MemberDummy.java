package com.gamemoonchul.domain.entity;

import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.domain.enums.MemberRole;

public class MemberDummy {
    public static Member create() {
        return Member.builder()
                .provider(OAuth2Provider.APPLE)
                .email("적길동@gmail.com")
                .nickname("적길동")
                .identifier("1234567890")
                .name("적길동")
                .picture("https://www.naver.com")
                .score(0.0)
                .role(MemberRole.USER)
                .build();
    }
}