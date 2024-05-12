package com.gamemoonchul.config.apple;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class AppleClientSecretGenerator {

    private final String APPLE_URL = "https://appleid.apple.com";
    @Value("${apple.privateKey}")
    private String PRIVATE_KEY;
    @Value("${apple.clientId}")
    private String APPLE_CLIENT_ID;
    @Value("${apple.teamId}")
    private String APPLE_TEAM_ID;
    @Value("${apple.keyId}")
    private String APPLE_KEY_ID;

    private PrivateKey generatePrivateKey() {
        try {
            System.out.println(PRIVATE_KEY);
            KeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder()
                    .decode(PRIVATE_KEY));
            return KeyFactory.getInstance("EC")
                    .generatePrivate(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public String create() throws IOException {
        Date expirationDate = Date.from(LocalDateTime.now()
                .plusDays(30)
                .atZone(ZoneId.systemDefault())
                .toInstant());
        Map<String, Object> jwtHeader = new HashMap<>();
        jwtHeader.put("kid", APPLE_KEY_ID);
        jwtHeader.put("alg", "ES256");

        return Jwts.builder()
                .setHeaderParams(jwtHeader)
                .setIssuer(APPLE_TEAM_ID)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 발행 시간 - UNIX 시간
                .setExpiration(expirationDate) // 만료 시간
                .setAudience(APPLE_URL)
                .setSubject(APPLE_CLIENT_ID)
                .signWith(generatePrivateKey())
                .compact();
    }
}