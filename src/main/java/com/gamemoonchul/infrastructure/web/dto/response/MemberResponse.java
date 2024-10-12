package com.gamemoonchul.infrastructure.web.dto.response;

import lombok.Builder;

@Builder
public record MemberResponse(
    Long id,
    String name,
    String nickname,
    String email,
    String picture,
    boolean privacyAgreed,
    Double score
) {

}
