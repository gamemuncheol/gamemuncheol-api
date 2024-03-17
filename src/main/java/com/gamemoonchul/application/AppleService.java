package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.config.apple.AppleIDTokenValidator;
import com.gamemoonchul.config.apple.entities.AppleCredential;
import com.gamemoonchul.config.apple.enums.AppleTokenStatus;
import com.gamemoonchul.config.jwt.TokenDto;
import com.gamemoonchul.config.jwt.TokenHelper;
import com.gamemoonchul.config.oauth.user.AppleOAuth2UserInfo;
import com.gamemoonchul.domain.entity.Member;
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

  public AppleCredential validateRequest(AppleSignUpRequestDto signUpRequest) {
    AppleCredential appleUserInfo = appleIDTokenValidator.extractAppleUserinfoFromIDToken(signUpRequest.getIdentityToken());
    if(signUpRequest.getName() == null || signUpRequest.getName().isEmpty()) {
      throw new ApiException(AppleTokenStatus.INVALID_SIGNUP_FORM);
    }
    appleUserInfo.setName(signUpRequest.getName());
    return appleUserInfo;
  }

  public TokenDto signInOrUp(AppleCredential credential) {
    Member member = MemberConverter.toEntity(credential);
    AppleOAuth2UserInfo userInfo = new AppleOAuth2UserInfo(credential);
    memberService.signInOrUp(member);
    TokenDto token  = tokenHelper.createToken(userInfo);
    return token;
  }
}
