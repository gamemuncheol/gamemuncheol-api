package com.gamemoonchul.domain.converter;

import com.gamemoonchul.config.apple.entities.AppleCredential;
import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.config.oauth.user.OAuth2UserInfo;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.enums.MemberRole;
import com.gamemoonchul.infrastructure.web.dto.MemberResponseDto;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MemberConverter {
    private static String randomNickname() {
        return UUID.randomUUID().toString().substring(0, 30);
    }

    public static Member toEntity(OAuth2UserInfo userInfo) {
        Optional<String> nickname = Optional.ofNullable(userInfo
                .getNickname());
        if (nickname.isEmpty()) {
            nickname = Optional.of(randomNickname());
        }
        Member member = Member.builder()
                .role(MemberRole.USER)
                .name(userInfo.getName())
                .identifier(userInfo.getIdentifier())
                .provider(userInfo.getProvider())
                .nickname(
                        nickname.get()
                )
                .privacyAgreed(false)
                .privacyAgreedAt(null)
                .score(0.0)
                .email(userInfo
                        .getEmail())
                .picture(userInfo
                        .getProfileImageUrl())
                .birth(null)
                .build();
        return member;
    }

    public static Member toEntity(AppleCredential userInfo) {
        Member member = Member.builder()
                .role(MemberRole.USER)
                .name(userInfo.getName())
                .identifier(userInfo.getSub()).provider(OAuth2Provider.APPLE)
                .nickname(randomNickname())
                .score(0.0)
                .privacyAgreed(false)
                .privacyAgreedAt(null)
                .email(userInfo.getEmail())
                .picture(null)
                .birth(null)
                .build();
        return member;
    }

    public static MemberResponseDto toResponseDto(Member entity) {
        return MemberResponseDto.builder()
                .name(entity.getName())
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .privacyAgreed(entity.isPrivacyAgreed())
                .picture(entity.getPicture())
                .score(entity.getScore())
                .build();
    }
}
