package com.gamemoonchul.config.apple.httpclient;

import com.gamemoonchul.config.apple.ApplePublicKeysManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

/************************************************************************
 Copyright 2020 eBay Inc.
 Author/Developer(s): Chetan Hibare; Dhairyasheel Desai; Swanand Abhyankar

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 https://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 **************************************************************************/

public class AppleHttpClient {

    private static final String APPLE_GET_PUBLIC_KEY_ENDPOINT = "https://appleid.apple.com/auth/keys";

    private static final AppleHttpClient appleHttpClient = new AppleHttpClient();

    private final RestTemplate restTemplate;

    private AppleHttpClient() {
        this.restTemplate = new RestTemplate();
    }

    public static AppleHttpClient getAppleHttpClient() {
        return appleHttpClient;
    }

    /**
     * Function that returns Apple's public key.
     *
     * @return Apple's public key in String
     * @throws IOException
     */
    public String fetchApplePublicKeyResponse() throws IOException {

        ApplePublicKeysManager manager = ApplePublicKeysManager.getApplePublicKeysManager();

        if(manager.isProxyEnabled()) {
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(manager.getProxyHost(), manager.getProxyPort()));
            requestFactory.setProxy(proxy);
            this.restTemplate.setRequestFactory(requestFactory);
        }

        ResponseEntity<String> response = this.restTemplate.getForEntity(APPLE_GET_PUBLIC_KEY_ENDPOINT, String.class);

        if(response.getStatusCode() != HttpStatus.OK) {
            throw new IOException("Error fetching Apple's public key");
        }

        return response.getBody();
    }
}