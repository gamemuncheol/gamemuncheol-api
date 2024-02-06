package com.gamemoonchul.application;

import com.gamemoonchul.config.apple.AppleIDTokenValidator;
import com.gamemoonchul.config.apple.entities.AppleUserInfo;
import com.gamemoonchul.config.jwt.TokenDto;
import com.gamemoonchul.config.jwt.TokenHelper;
import com.gamemoonchul.domain.MemberEntity;
import com.gamemoonchul.domain.converter.MemberConverter;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.web.dto.AppleSignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppleService {
  private final AppleIDTokenValidator appleIDTokenValidator;
  private final MemberRepository memberRepository;
  private final TokenHelper tokenHelper;

  public TokenDto signUp(AppleSignUpRequestDto signUpRequest) {
    AppleUserInfo appleUserInfo = appleIDTokenValidator.extractAppleUserinfoFromIDToken(signUpRequest.getIdentityToken());
    appleUserInfo.setName(signUpRequest.getName());

    MemberEntity member = MemberConverter.toEntity(appleUserInfo);
    memberRepository.save(member);

    TokenDto token  = tokenHelper.createToken(member.getEmail());
    return token;
  }
}
