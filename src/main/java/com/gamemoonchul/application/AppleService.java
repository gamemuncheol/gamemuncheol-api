package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.config.apple.AppleIDTokenValidator;
import com.gamemoonchul.config.apple.entities.AppleUserInfo;
import com.gamemoonchul.config.apple.enums.AppleTokenStatus;
import com.gamemoonchul.config.jwt.TokenDto;
import com.gamemoonchul.config.jwt.TokenHelper;
import com.gamemoonchul.domain.MemberEntity;
import com.gamemoonchul.domain.converter.MemberConverter;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.web.dto.AppleSignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppleService {
  private final AppleIDTokenValidator appleIDTokenValidator;
  private final MemberRepository memberRepository;
  private final TokenHelper tokenHelper;

  public TokenDto signUp(AppleSignUpRequestDto signUpRequest) {
    AppleUserInfo appleUserInfo = appleIDTokenValidator.extractAppleUserinfoFromIDToken(signUpRequest.getIdentityToken());
    if(signUpRequest.getName() == null || signUpRequest.getName().isEmpty()) {
      throw new ApiException(AppleTokenStatus.INVALID_SIGNUP_FORM);
    }
    appleUserInfo.setName(signUpRequest.getName());

    Optional<MemberEntity> alreadyExistMember = memberRepository.findTop1ByEmail(appleUserInfo.getEmail());
    MemberEntity member = new MemberEntity();

    if (alreadyExistMember.isEmpty()) {
      member = MemberConverter.toEntity(appleUserInfo);
      memberRepository.save(member);
    } else {
      member = alreadyExistMember.get();
    }
    TokenDto token  = tokenHelper.createToken(member.getEmail());

    return token;
  }
}
