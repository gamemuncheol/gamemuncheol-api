package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.MemberBanService;
import com.gamemoonchul.application.member.MemberConverter;
import com.gamemoonchul.application.member.MemberService;
import com.gamemoonchul.common.annotation.MemberSession;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.MemberBan;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.response.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerWithEnvelopPattern
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;
    private final MemberBanService memberBanService;

    @PatchMapping("/nickname/{nickname}")
    public void changeNickname(
            @MemberSession Member member,
            @PathVariable(name = "nickname") String nickname
    ) {
        memberService.updateNickName(member, nickname);
    }


    @GetMapping("/ban")
    public List<MemberResponseDto> ban(
            @MemberSession Member member
    ) {
        return memberBanService.bannedMembers(member.getId()).stream()
                .map(MemberBan::getBanMember)
                .map(MemberConverter::toResponseDto).toList();
    }

    @GetMapping("/me")
    public MemberResponseDto me(
            @MemberSession Member member
    ) {
        return memberService.me(member);
    }


    @DeleteMapping
    public void delete(
            @MemberSession Member member
    ) {
        memberService.delete(member);
    }

    @PostMapping("/ban/{banMemberId}")
    public void ban(
            @MemberSession Member member,
            @PathVariable(name = "banMemberId") Long banMemberId
    ) {
        memberBanService.ban(member, banMemberId);
    }

    @DeleteMapping("/ban/{banMemberId}")
    public void deleteBan(
            @MemberSession Member member,
            @PathVariable(name = "banMemberId") Long banMemberId
    ) {
        memberBanService.deleteBan(member, banMemberId);
    }
}
