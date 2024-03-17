package com.gamemoonchul.domain.converter;

import com.gamemoonchul.config.apple.entities.AppleUserInfo;
import com.gamemoonchul.config.oauth.user.OAuth2UserInfo;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.enums.Issuer;
import com.gamemoonchul.domain.enums.MemberRole;

import java.util.Optional;

public class MemberConverter {
    public static Member toEntity(OAuth2UserInfo userInfo) {
        Optional<String> nickname = Optional.ofNullable(userInfo
                .getNickname());
        if (nickname.isEmpty()) {
            nickname = Optional.ofNullable(userInfo
                    .getId());
        }
        Member member = Member.builder()
                .role(MemberRole.USER)
                .name(userInfo.getName())
                .nickname(
                        nickname.get()
                )
                .score(0.0)
                .email(userInfo
                        .getEmail())
                .picture(userInfo
                        .getProfileImageUrl())
                .birth(null)
                .build();
        return member;
    }

    public static Member toEntity(AppleUserInfo userInfo) {
        Member member = Member.builder()
                .role(MemberRole.USER)
                .name(userInfo.getName())
                .identifier(userInfo.getSub()).issuer(Issuer.APPLE)
                .nickname(userInfo.getClientId())
                .score(0.0)
                .email(userInfo.getEmail())
                .picture(null)
                .birth(null)
                .build();
        return member;
    }
}
