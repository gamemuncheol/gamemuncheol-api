package com.gamemoonchul.config.oauth.user;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.domain.status.Oauth2Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserInfoFactory {
  private final RestTemplate restTemplate;

  public static OAuth2UserInfo getOAuth2UserInfo(String registrationId,
                                                 String accessToken,
                                                 Map<String, Object> attributes) {
    if (OAuth2Provider.GOOGLE.getRegistrationId()
        .equals(registrationId)) {
      return new GoogleOAuth2UserInfo(accessToken, attributes);
    } else {
      throw new ApiException(Oauth2Status.NOT_FOUND_PROVIDER);
    }
  }
}