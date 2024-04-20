package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.MemberService;
import com.gamemoonchul.common.annotation.MemberSession;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

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
        memberService.updateNickName(member, nickname);
    }

    @GetMapping("/me")
    public MemberResponseDto me(
            @MemberSession Member member
    ) {
        return memberService.me(Optional.of(member));
    }
}
