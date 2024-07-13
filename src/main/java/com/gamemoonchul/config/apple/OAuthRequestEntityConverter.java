package com.gamemoonchul.config.apple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

@Slf4j
public class OAuthRequestEntityConverter implements Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> {

    private final AppleClientSecretGenerator appleClientSecretGenerator;
    private final OAuth2AuthorizationCodeGrantRequestEntityConverter defaultConverter;

    public OAuthRequestEntityConverter(AppleClientSecretGenerator appleClientSecretGenerator) {
        defaultConverter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();
        this.appleClientSecretGenerator = appleClientSecretGenerator;
    }

    @Override
    public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest req) {
        RequestEntity<?> entity = defaultConverter.convert(req);
        String registrationId = req.getClientRegistration()
                .getRegistrationId();
        MultiValueMap<String, String> params = (MultiValueMap<String, String>) entity.getBody();
        if (registrationId.contains("apple")) {
            try {
                params.set("client_secret", appleClientSecretGenerator.create());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new RequestEntity<>(params, entity.getHeaders(),
                entity.getMethod(), entity.getUrl());
    }

}