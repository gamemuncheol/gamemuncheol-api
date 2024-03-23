package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.MemberService;
import com.gamemoonchul.config.jwt.TokenDto;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.RenewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/open-api/member")
@RestControllerWithEnvelopPattern
public class MemberOpenApiController {
    private final MemberService memberService;

    @PostMapping("/renew")
    public TokenDto renew(@RequestBody RenewRequest request) {
        return memberService.renew(request.refreshToken());
    }
}
