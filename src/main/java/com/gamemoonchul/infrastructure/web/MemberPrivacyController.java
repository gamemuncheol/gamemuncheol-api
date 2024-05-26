package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.MemberService;
import com.gamemoonchul.common.annotation.MemberSession;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.MemberResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RestControllerWithEnvelopPattern
@RequestMapping("/privacy")
public class MemberPrivacyController {
    private final MemberService memberService;

    @PatchMapping
    @Operation(description = "개인정보 처리방침 동의로 업데이트 하는 API")
    public MemberResponseDto privacyAgree(@MemberSession Member member) {
        return memberService.privacyAgree(member);
    }

    @GetMapping
    @Operation(description = "개인정보 처리방침 동의했는지 확인하는 API")
    public boolean isAgreedPrivacy(@MemberSession Member member) {
        return member.isPrivacyAgreed();
    }
}
