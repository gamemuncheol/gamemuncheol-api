package com.gamemoonchul.config.apple;

import com.google.gson.annotations.SerializedName;
import lombok.*;

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

@Builder @Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppleCredential {
    /**
     * https://developer.apple.com/documentation/signinwithapplerestapi/authenticating_users_with_sign_in_with_apple
     */

    /**
     * The issuer-registered claim key, which has the value https://appleid.apple.com.
     */
    @SerializedName("iss")
    private String issuer;

    @Getter @Setter
    private String name;


    /**
     * 애플 고유 식별자
     */
    @SerializedName("sub")
    private String sub;


    /**
     * Your client_id in your Apple Developer account.
     */
    @SerializedName("aud")
    private String clientId;


    /**
     * The expiry time for the token. This value is typically set to 5 minutes.
     */
    @SerializedName("exp")
    private String expiryTime;


    /**
     * The time the token was issued.
     */
    @SerializedName("iat")
    private String issuingTime;


    /**
     * A String value used to associate a client session and an ID token. This value is used to mitigate replay attacks
     * and is present only if passed during the authorization request.
     */
    @SerializedName("nonce")
    private String nonce;


    /**
     * The user's email address.
     */
    @SerializedName("email")
    private String email;


    /**
     * A Boolean value that indicates whether the service has verified the email.
     * The value of this claim is always true because the servers only return verified email addresses.
     */
    @SerializedName("email_verified")
    private boolean emailVerified;
}