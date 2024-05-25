package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.MemberService;
import com.gamemoonchul.common.annotation.MemberSession;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestControllerWithEnvelopPattern
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PatchMapping("/change-nickname/{nickname}")
    public void changeNickname(
            @MemberSession Member member,
            @PathVariable(name = "nickname") String nickname
    ) {
        memberService.updateNickName(member, nickname);
    }


    @GetMapping("/duplication")
    public boolean checkDuplicated(
            @RequestParam(name = "nickname") String nickname
    ) {
        return memberService.checkDuplicated(nickname);
    }


    @GetMapping("/me")
    public MemberResponseDto me(
            @MemberSession Member member
    ) {
        return memberService.me(member);
    }
}
