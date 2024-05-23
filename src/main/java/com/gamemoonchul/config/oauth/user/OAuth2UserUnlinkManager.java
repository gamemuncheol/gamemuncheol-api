package com.gamemoonchul.config.oauth.user;

import com.gamemoonchul.common.exception.InternalServerException;
import com.gamemoonchul.domain.status.Oauth2Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2UserUnlinkManager {

  private final GoogleOAuth2UserUnlink googleOAuth2UserUnlink;

  public void unlink(OAuth2Provider provider, String accessToken) {
    if (OAuth2Provider.GOOGLE.equals(provider)) {
      googleOAuth2UserUnlink.unlink(accessToken);
    } else {
      log.error(Oauth2Status.UNLINK_FAILED.getMessage());
      throw new InternalServerException(Oauth2Status.UNLINK_FAILED);
    }
  }
}
