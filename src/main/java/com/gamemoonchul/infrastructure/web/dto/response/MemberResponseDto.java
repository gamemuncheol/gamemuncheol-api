package com.gamemoonchul.infrastructure.web.dto.response;

import com.gamemoonchul.domain.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String name;
    private String nickname;
    private String email;
    private String picture;
    private boolean privacyAgreed;
    private Double score;

    public MemberResponseDto(Member entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.nickname = entity.getNickname();
        this.email = entity.getEmail();
        this.privacyAgreed = entity.isPrivacyAgreed();
        this.picture = entity.getPicture();
        this.score = entity.getScore();
    }
}
