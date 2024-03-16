package com.gamemoonchul.config.apple;

import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.config.apple.entities.AppleUserInfo;
import com.gamemoonchul.config.apple.enums.AppleTokenStatus;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.List;


@Component
public class AppleIDTokenValidator {

    private static final String APPLE_PUBLIC_KEYS_DOMAIN = "https://appleid.apple.com/auth/keys";
    private static final String ISSUER = "https://appleid.apple.com";
    @Value("${apple.clientId}")
    private  String CLIENT_ID;
    @Value("${apple.nonce}")
    private String NONCE;

    private JwkProvider jwkProvider = new JwkProviderBuilder(APPLE_PUBLIC_KEYS_DOMAIN).build();

    private void verify(String identityToken) {
        DecodedJWT decodedJWT = getDecodedJwtOrThrow(identityToken);
        verifyPublicKey(decodedJWT);
        verifyIssuer(decodedJWT.getIssuer());
        verifyNonce(decodedJWT.getClaim("nonce").asString());
        verifyAudience(decodedJWT.getAudience());
        verifyExpiration(decodedJWT.getExpiresAt());
    }

    public AppleUserInfo extractAppleUserinfoFromIDToken(String idToken) {
        AppleUserInfo appleUserInfo = null;
        verify(idToken);

        try {
            String payload = new String(Base64.decodeBase64(idToken.split("\\.")[1]));
            appleUserInfo = new Gson().fromJson(payload, AppleUserInfo.class);
        } catch (JsonSyntaxException e) {
            throw new ApiException(AppleTokenStatus.INVALID_ID_TOKEN);
        }
        if (appleUserInfo == null) throw new ApiException(AppleTokenStatus.INVALID_ID_TOKEN);
        return appleUserInfo;
    }


    private DecodedJWT getDecodedJwtOrThrow(String identityToken) {
        try {
            return JWT.decode(identityToken);
        } catch (JWTDecodeException e) {
            throw new JWTDecodeException("Failed to decode token - " + identityToken + " - " + e.getMessage());
        }
    }

    private void verifyPublicKey(DecodedJWT decodedJWT) {
        Algorithm algorithm = null;
        String keyId = decodedJWT.getKeyId();
        try {
            algorithm = Algorithm.RSA256((RSAPublicKey) jwkProvider.get(keyId).getPublicKey());
        } catch (JwkException e) {
            throw new ApiException(AppleTokenStatus.UNSUPPORTED_ID_TOKEN);
        }

        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();

        try {
            jwtVerifier.verify(decodedJWT);
        } catch (JWTVerificationException e) {
            throw new ApiException(AppleTokenStatus.INVALID_ID_TOKEN);
        }
    }

    private void verifyNonce(String nonce) {
        if (!NONCE.equals(nonce)) {
            throw new ApiException(AppleTokenStatus.INVALID_NONCE);
        }
    }

    private void verifyIssuer(String issuer) {
        if (!ISSUER.equals(issuer)) {
            throw new ApiException(AppleTokenStatus.INVALID_ID_TOKEN);
        }
    }

    private void verifyAudience(List<String> audience) {
        if (audience.isEmpty() || !CLIENT_ID.equals(audience.get(0))) {
            throw new ApiException(AppleTokenStatus.INVALID_AUDIENCE);
        }
    }

    private void verifyExpiration(Date expiresAt) {
        if (expiresAt.before(new Date())) {
            throw new ApiException(AppleTokenStatus.EXPIRED_ID_TOKEN);
        }
    }
}