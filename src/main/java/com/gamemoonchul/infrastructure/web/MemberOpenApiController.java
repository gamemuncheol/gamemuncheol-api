package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.MemberOpenApiService;
import com.gamemoonchul.config.jwt.TokenDto;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.request.RegisterRequest;
import com.gamemoonchul.infrastructure.web.dto.request.RenewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/open-api/members")
@RestControllerWithEnvelopPattern
public class MemberOpenApiController {
    private final MemberOpenApiService memberOpenApiService;

    @PostMapping("/renew")
    public TokenDto renew(@RequestBody RenewRequest request) {
        return memberOpenApiService.renew(request.refreshToken());
    }

    @GetMapping("/nickname/{nickname}")
    public boolean checkNicknameDuplicated(
            @PathVariable(name = "nickname") String nickname
    ) {
        return memberOpenApiService.isExistNickname(nickname);
    }


    @PostMapping("/register")
    public TokenDto register(@RequestBody RegisterRequest request) {
        return memberOpenApiService.register(request);
    }
}
