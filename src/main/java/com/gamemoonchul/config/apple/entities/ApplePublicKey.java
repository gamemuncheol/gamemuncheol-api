package com.gamemoonchul.config.apple.entities;

import com.google.gson.annotations.SerializedName;

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

public class ApplePublicKey {

    /**
     * https://developer.apple.com/documentation/signinwithapplerestapi/jwkset/keys
     */

    /**
     * The key type parameter setting. This must be set to "RSA".
     */
    @SerializedName("kty")
    private String kty;

    /**
     * A 10-character identifier key, obtained from your developer account.
     */
    @SerializedName("kid")
    private String kid;

    /**
     * The intended use for the public key.
     */
    @SerializedName("use")
    private String use;

    /**
     * The encryption algorithm used to encrypt the token.
     */
    @SerializedName("alg")
    private String alg;

    /**
     * The modulus value for the RSA public key.
     */
    @SerializedName("n")
    private String n;

    /**
     * The exponent value for the RSA public key.
     */
    @SerializedName("e")
    private String e;


    public String getKty() {
        return kty;
    }

    public String getKid() {
        return kid;
    }

    public String getUse() {
        return use;
    }

    public String getAlg() {
        return alg;
    }

    public String getModulus() {
        return n;
    }

    public String getExponent() {
        return e;
    }
}