package com.gamemoonchul.application.converter;

import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.redis.RedisMember;
import com.gamemoonchul.domain.enums.MemberRole;
import com.gamemoonchul.infrastructure.web.dto.response.MemberResponseDto;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class MemberConverter {
    private static String randomNickname() {
        return UUID.randomUUID()
            .toString()
            .substring(0, 30);
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
            .id(entity.getId())
            .name(entity.getName())
            .nickname(entity.getNickname())
            .email(entity.getEmail())
            .privacyAgreed(entity.isPrivacyAgreed())
            .picture(entity.getPicture())
            .score(entity.getScore())
            .build();
    }
}
