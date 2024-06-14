package com.gamemoonchul.application.converter;

import com.gamemoonchul.config.apple.AppleCredential;
import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.config.oauth.user.OAuth2UserInfo;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.redis.RedisMember;
import com.gamemoonchul.domain.enums.MemberRole;
import com.gamemoonchul.infrastructure.web.dto.MemberResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemberConverter {
    private static String randomNickname() {
        return UUID.randomUUID()
                .toString()
                .substring(0, 30);
    }

    public static Member toEntity(OAuth2UserInfo userInfo) {
        String nickname = ifNicknameNotExistGetRandom(userInfo
                .getNickname());
        Member member = Member.builder()
                .role(MemberRole.PRIVACY_NOT_AGREED)
                .name(userInfo.getName())
                .identifier(userInfo.getIdentifier())
                .provider(userInfo.getProvider())
                .nickname(nickname)
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

    public static RedisMember toRedisMember(OAuth2UserInfo userInfo) {
        return RedisMember.builder()
                .name(userInfo.getName())
                .provider(userInfo.getProvider())
                .identifier(userInfo.getIdentifier())
                .nickname(userInfo.getNickname())
                .email(userInfo.getEmail())
                .picture(userInfo.getProfileImageUrl())
                .birth(null)
                .build();
    }

    public static Member redisMemberToEntity(RedisMember redisMember) {
        String nickname = ifNicknameNotExistGetRandom(redisMember
                .getNickname());

        Member member = Member.builder()
                .role(MemberRole.USER)
                .name(redisMember.getName())
                .identifier(redisMember.getIdentifier())
                .provider(redisMember.getProvider())
                .nickname(nickname)
                .privacyAgreed(true)
                .privacyAgreedAt(LocalDateTime.now())
                .score(0.0)
                .email(redisMember.getEmail())
                .picture(redisMember.getPicture())
                .birth(null)
                .build();
        return member;
    }

    private static String ifNicknameNotExistGetRandom(String currentNickname) {
        Optional<String> nickname = Optional.ofNullable(currentNickname);
        if (nickname.isEmpty()) {
            nickname = Optional.of(randomNickname());
        }
        return nickname.get();
    }

    public static Member toEntity(AppleCredential userInfo) {
        Member member = Member.builder()
                .role(MemberRole.PRIVACY_NOT_AGREED)
                .name(userInfo.getName())
                .identifier(userInfo.getSub())
                .provider(OAuth2Provider.APPLE)
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
