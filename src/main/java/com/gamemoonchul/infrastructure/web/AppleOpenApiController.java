package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.application.AppleService;
import com.gamemoonchul.config.apple.AppleIDTokenValidator;
import com.gamemoonchul.config.apple.entities.AppleUserInfo;
import com.gamemoonchul.config.jwt.TokenDto;
import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.dto.AppleSignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestControllerWithEnvelopPattern
@RequestMapping("/open-api/apple")
public class AppleOpenApiController {
  private final AppleService appleService;

  @PostMapping("/sign-up")
  public TokenDto login(@RequestBody AppleSignUpRequestDto signUpRequest) {
    TokenDto token = appleService.signUp(signUpRequest);

    return token;
  }
}
