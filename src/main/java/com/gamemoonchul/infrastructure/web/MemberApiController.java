package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.MemberService;
import com.gamemoonchul.common.annotation.MemberSession;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestControllerWithEnvelopPattern
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PatchMapping("/change-nickname/{nickname}")
    public void changeNickname(
            @MemberSession Member member,
            @PathVariable String nickname
    ) {
        memberService.updateNickNameOrThrow(member, nickname);
    }
}
