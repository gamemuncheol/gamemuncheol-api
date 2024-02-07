package com.gamemoonchul.config.apple;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.config.apple.entities.AppleUserInfo;
import com.gamemoonchul.config.apple.enums.AppleTokenStatus;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.List;


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
@Component
public class AppleIDTokenValidator {

    private static final String SEPARATOR_PERIOD = "\\.";
    private static final int ID_TOKEN_PARTS = 3;
    private static final String APPLE_ISSUER = "https://appleid.apple.com";

    private static List<PublicKey> applePublicKeys = null;
    private ApplePublicKeysManager applePublicKeysManager;

    public AppleIDTokenValidator() {
        applePublicKeysManager = ApplePublicKeysManager.getApplePublicKeysManager();
    }

    /**
     * Function that verifies ID token's signature and content against client ids and nonce.
     *
     * @param idToken Input ID token
     * @param clientIds Client Ids to validate against
     * @param nonce Nonce to validate against
     * @return true/false based on validation
     * @throws com.gamemoonchul.common.exception.ApiException
     */
    public boolean verifyAppleIDToken(String idToken, List<String> clientIds, String nonce) {

        if(isValidIDToken(idToken) && verifyTokenPayload(extractAppleUserinfoFromIDToken(idToken), clientIds, nonce)) {
            if (applePublicKeys == null) {
                applePublicKeys = applePublicKeysManager.getApplePublicKeys();
            }
            for (PublicKey publicKey : applePublicKeys) {
                if (verifySignature(idToken, publicKey)) {
                    return true;
                } else {
                    applePublicKeys = applePublicKeysManager.getApplePublicKeys();
                }
            }
        }
        return false;
    }

    private boolean verifySignature(String idToken, PublicKey publicKey) {
        try {
            Jwts.parser().setSigningKey(publicKey).parseClaimsJws(idToken);
        } catch (SignatureException e) {
            return false;
        } catch (MalformedJwtException e) {
            throw new ApiException(AppleTokenStatus.INVALID_ID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new ApiException(AppleTokenStatus.EXPIRED_ID_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new ApiException(AppleTokenStatus.UNSUPPORTED_ID_TOKEN);
        } catch (Exception e) {
            throw new ApiException(AppleTokenStatus.INVALID_ID_TOKEN_SIGNATURE);
        }
        return true;
    }

    /**
     * Function that extracts user information from ID token.
     *
     * @param idToken Input ID token
     * @return AppleUserInfo object containing user details
     * @throws
     */
    public AppleUserInfo extractAppleUserinfoFromIDToken(String idToken) {
        AppleUserInfo appleUserInfo = null;
        if(isValidIDToken(idToken)) {
            try {
                String payload = new String(Base64.decodeBase64(idToken.split(SEPARATOR_PERIOD)[1]));
                appleUserInfo = new Gson().fromJson(payload, AppleUserInfo.class);
            } catch (JsonSyntaxException e) {
                throw new ApiException(AppleTokenStatus.INVALID_ID_TOKEN);
            }
            if (appleUserInfo == null) throw new ApiException(AppleTokenStatus.INVALID_ID_TOKEN);
            return appleUserInfo;
        } else {
            throw new ApiException(AppleTokenStatus.INVALID_ID_TOKEN);
        }
    }

    private boolean isValidIDToken(String idToken) {
    	if (idToken.isBlank() || idToken.length() < 5) return false;
    	String[] idTokenStrings = idToken.split(SEPARATOR_PERIOD);
        if (idTokenStrings == null
        		|| idTokenStrings.length != ID_TOKEN_PARTS
                || idTokenStrings[0].isBlank()
                || idTokenStrings[1].isBlank()
                || idTokenStrings[2].isBlank()) {
            return false;
        }
        return true;
    }

    /**
        Verify the JWS E256 signature using the server’s public key

        Verify the nonce for the authentication

        Verify that the iss field contains https://appleid.apple.com

        Verify that the aud field is the developer’s client_id

        Verify that the time is earlier than the exp value of the token
     */
    private boolean verifyTokenPayload(AppleUserInfo appleUserInfo, List<String> originalClientIds, String originalNonce) {

        boolean isValidIssuer = !appleUserInfo.getIssuer().isBlank()
                                && appleUserInfo.getIssuer().contains(APPLE_ISSUER);

        boolean isValidClientId = true;
        if(originalClientIds != null) {
            isValidClientId = !appleUserInfo.getClientId().isBlank()
                    && originalClientIds.contains(appleUserInfo.getClientId());
        }

        boolean isValidNonce = originalNonce.isBlank() ? true : appleUserInfo.getNonce().isBlank()
                                && appleUserInfo.getNonce().equals(originalNonce);

        boolean isValidExpirationTime = appleUserInfo.getExpiryTime().isBlank();

        return isValidIssuer && isValidClientId && isValidNonce && isValidExpirationTime;
    }

}