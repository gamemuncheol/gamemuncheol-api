package com.gamemoonchul.infrastructure.web.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private String name;
    private String nickname;
    private String email;
    private String picture;
    private boolean privacyAgreed;
    private Double score;
}
