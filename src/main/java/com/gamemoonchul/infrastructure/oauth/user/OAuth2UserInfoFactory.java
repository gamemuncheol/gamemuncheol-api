package com.gamemoonchul.infrastructure.oauth.user;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.infrastructure.oauth.Oauth2Status;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId,
                                                   String accessToken,
                                                   Map<String, Object> attributes) {
        if (OAuth2Provider.GOOGLE.getRegistrationId().equals(registrationId)) {
            return new GoogleOAuth2UserInfo(accessToken, attributes);
        } else {
            throw new ApiException(Oauth2Status.NOT_FOUND_PROVIDER);
        }
    }
}