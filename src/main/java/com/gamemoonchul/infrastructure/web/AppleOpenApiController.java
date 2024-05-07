package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.AppleService;
import com.gamemoonchul.config.apple.entities.AppleCredential;
import com.gamemoonchul.config.jwt.TokenDto;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.AppleSignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RestControllerWithEnvelopPattern
@RequestMapping("/open-api/apple")
public class AppleOpenApiController {
  private final AppleService appleService;

  @PostMapping("/sign-in")
  public TokenDto signIn(@RequestBody AppleSignUpRequestDto signUpRequest) {
    AppleCredential userInfo = appleService.validateRequest(signUpRequest);
    TokenDto token = appleService.signIn(userInfo);
    return token;
  }
}
