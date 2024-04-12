package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.MemberService;
import com.gamemoonchul.common.annotation.MemberSession;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RestControllerWithEnvelopPattern
@RequestMapping("/privacy")
public class MemberPrivacyController {
    private final MemberService memberService;

    @PatchMapping("/agree")
    public MemberResponseDto agreePrivacy(@MemberSession Member member) {
        return memberService.privacyAgree(member);
    }

    @GetMapping("/is-agreed")
    public boolean isAgreedPrivacy(@MemberSession Member member) {
        return member.isPrivacyAgreed();
    }
}
