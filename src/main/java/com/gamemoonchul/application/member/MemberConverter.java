package com.gamemoonchul.application.member;

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
