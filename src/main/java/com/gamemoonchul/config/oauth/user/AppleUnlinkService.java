package com.gamemoonchul.config.oauth.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Component
@RequiredArgsConstructor
public class AppleUnlinkService {
    private static final String URL = "https://appleid.apple.com/auth/revoke";
    private final RestTemplate restTemplate;
    @Value("${apple.clientId}")
    private String APPLE_CLIENT_ID;
    @Value("${apple.privateKey}")
    private String PRIVATE_KEY;

    /*
     * 참조 : https://velog.io/@givepro91/jjo2cyus#%EC%95%A0%ED%94%8C-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%ED%9A%8C%EC%9B%90-%ED%83%88%ED%87%B4-%EC%8B%9C-apple-server%EB%A1%9C-%EC%82%AC%EC%9A%A9%EC%9E%90-%ED%86%A0%ED%81%B0-%ED%95%B4%EC%A7%80-%EC%B2%98%EB%A6%AC
     */
    public void unlink(String accessToken) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", APPLE_CLIENT_ID);
        params.add("client_secret", PRIVATE_KEY);
        params.add("token", accessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        restTemplate.postForEntity(URL, httpEntity, String.class);
    }
}
