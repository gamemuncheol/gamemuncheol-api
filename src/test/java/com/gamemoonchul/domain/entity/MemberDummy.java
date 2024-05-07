package com.gamemoonchul.domain.entity;

import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.domain.enums.MemberRole;

import java.util.ArrayList;
import java.util.List;

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

    public static Member createWithId(String id) {
        return Member.builder()
                .provider(OAuth2Provider.GOOGLE)
                .email(id + "@gmail.com")
                .nickname(id)
                .identifier(id)
                .name(id)
                .picture("https://www.naver.com")
                .score(0.0)
                .role(MemberRole.USER)
                .build();
    }

    /**
     * 다수의 멤버를 생성해주는 메서드
     *
     * @param count 생성할 멤버의 수
     * @return 생성된 멤버들
     */
    public static List<Member> createUserRoleMembers(Integer count) {
        List<Member> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            result.add(Member.builder()
                    .provider(OAuth2Provider.GOOGLE)
                    .email("test" + i + "@gmail.com")
                    .nickname("test" + i)
                    .identifier("test" + i)
                    .name("test" + i)
                    .picture("https://www.naver.com")
                    .score(0.0)
                    .role(MemberRole.USER)
                    .build());
        }
        return result;
    }


    public static Member createPrivacyRole() {
        return Member.builder()
                .provider(OAuth2Provider.GOOGLE)
                .email("test1@gmail.com")
                .nickname("홍길동")
                .identifier("test1")
                .name("홍길동")
                .picture("https://www.naver.com")
                .score(0.0)
                .role(MemberRole.PRIVACY_NOT_AGREED)
                .build();
    }

}