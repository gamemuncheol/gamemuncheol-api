package com.gamemoonchul.application.converter;

import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.infrastructure.web.dto.response.MemberResponseDto;

public class MemberConverter {
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
