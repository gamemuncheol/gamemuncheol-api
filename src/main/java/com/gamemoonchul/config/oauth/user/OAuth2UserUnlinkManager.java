package com.gamemoonchul.config.oauth.user;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.domain.status.Oauth2Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OAuth2UserUnlinkManager {

  private final GoogleOAuth2UserUnlink googleOAuth2UserUnlink;

  public void unlink(OAuth2Provider provider, String accessToken) {
    if (OAuth2Provider.GOOGLE.equals(provider)) {
      googleOAuth2UserUnlink.unlink(accessToken);
    } else {
      throw new ApiException(Oauth2Status.UNLINK_FAILED);
    }
  }
}
