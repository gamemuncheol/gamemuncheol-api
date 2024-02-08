package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.config.apple.AppleIDTokenValidator;
import com.gamemoonchul.config.apple.entities.AppleUserInfo;
import com.gamemoonchul.config.apple.enums.AppleTokenStatus;
import com.gamemoonchul.config.jwt.TokenDto;
import com.gamemoonchul.config.jwt.TokenHelper;
import com.gamemoonchul.domain.MemberEntity;
import com.gamemoonchul.domain.converter.MemberConverter;
import com.gamemoonchul.infrastructure.web.dto.AppleSignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppleService {
  private final AppleIDTokenValidator appleIDTokenValidator;
  private final MemberService memberService;
  private final TokenHelper tokenHelper;

  public AppleUserInfo validateRequest(AppleSignUpRequestDto signUpRequest) {
    AppleUserInfo appleUserInfo = appleIDTokenValidator.extractAppleUserinfoFromIDToken(signUpRequest.getIdentityToken());
    if(signUpRequest.getName() == null || signUpRequest.getName().isEmpty()) {
      throw new ApiException(AppleTokenStatus.INVALID_SIGNUP_FORM);
    }
    appleUserInfo.setName(signUpRequest.getName());
    return appleUserInfo;
  }

  public TokenDto signInOrUp(AppleUserInfo appleUserInfo) {
    MemberEntity member = MemberConverter.toEntity(appleUserInfo);
    memberService.signInOrUp(member);
    TokenDto token  = tokenHelper.createToken(member.getEmail());
    return token;
  }
}
