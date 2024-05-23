package com.gamemoonchul.config.oauth.service;

import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.common.exception.InternalServerException;
import com.gamemoonchul.config.oauth.OAuth2UserPrincipal;
import com.gamemoonchul.domain.status.Oauth2Status;
import com.gamemoonchul.config.oauth.user.OAuth2UserInfoFactory;
import com.gamemoonchul.config.oauth.user.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final OAuth2UserInfoFactory oAuth2UserInfoFactory;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

    try {
      return processOAuth2User(oAuth2UserRequest, oAuth2User);
    } catch (AuthenticationException ex) {
      // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
      throw ex;
    } catch (Exception ex) {
      log.error(Oauth2Status.LOGIN_FAILED.getMessage());
      throw new InternalServerException(Oauth2Status.LOGIN_FAILED);
    }
  }

  private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

    String registrationId = userRequest.getClientRegistration()
        .getRegistrationId();

    String accessToken = userRequest.getAccessToken()
        .getTokenValue();

    OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId,
        accessToken,
        oAuth2User.getAttributes());

    // OAuth2UserInfo field value validation
    if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
      log.error(Oauth2Status.NOT_FOUND_EMAIL.getMessage());
      throw new BadRequestException(Oauth2Status.NOT_FOUND_EMAIL);
    }

    return new OAuth2UserPrincipal(oAuth2UserInfo);
  }
}
