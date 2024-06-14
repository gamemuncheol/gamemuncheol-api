package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.MemberOpenApiService;
import com.gamemoonchul.config.jwt.TokenDto;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.RenewRequest;
import com.gamemoonchul.infrastructure.web.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/open-api/members")
@RestControllerWithEnvelopPattern
public class MemberOpenApiController {
    private final MemberOpenApiService memberService;

    @PostMapping("/renew")
    public TokenDto renew(@RequestBody RenewRequest request) {
        return memberService.renew(request.refreshToken());
    }

    @PostMapping("/register")
    public TokenDto register(@RequestBody RegisterRequest request) {
        return memberService.register(request);
    }
}
