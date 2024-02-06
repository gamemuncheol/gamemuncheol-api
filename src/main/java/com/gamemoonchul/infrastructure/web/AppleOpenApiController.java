package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.config.apple.AppleIDTokenValidator;
import com.gamemoonchul.config.apple.entities.AppleUserInfo;
import com.gamemoonchul.infrastructure.web.dto.AppleLoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open-api/apple")
@RequiredArgsConstructor
public class AppleOpenApiController {
  private final AppleIDTokenValidator appleIDTokenValidator;
  @RequestMapping("/login")
  public AppleUserInfo login(@RequestBody AppleLoginRequestDto loginRequest) {
    AppleUserInfo appleUserInfo = appleIDTokenValidator.extractAppleUserinfoFromIDToken(loginRequest.getIdentityToken());
    return appleUserInfo;
  }
}
