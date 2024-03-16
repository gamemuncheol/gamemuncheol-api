package com.gamemoonchul.config.apple.enums;

import com.gamemoonchul.common.status.ApiStatusIfs;
import lombok.Getter;

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

/**
 * Apple Token 에러
 * 50000~51000
 */

// Modified by rookedsysc
    @Getter
public enum AppleTokenStatus implements ApiStatusIfs {

    // ID Token related errors
    INVALID_ID_TOKEN_SIGNATURE(50100, "ID Token signature verification failed"),
    INVALID_SIGNUP_FORM(50105, "회원가입에 실패했습니다. 최초 회원 가입이 아니거나 이름을 제공하지 않았습니다."),
    INVALID_ID_TOKEN(50200,"Invalid ID Token"),
    EXPIRED_ID_TOKEN(50300, "Expired ID Token"),
    UNSUPPORTED_ID_TOKEN(50400, "Unsupported exception in Apple ID Token validation"),
    INVALID_NONCE(50450, "Invalid nonce in ID Token"),
    INVALID_ISSUER(50451, "Invalid issuer in ID Token"),
    INVALID_AUDIENCE(50452, "Invalid audience in ID Token"),

    // Apple specific errors
    PROXY_SETUP_ERROR(50500, "Invalid proxy host and/or port"),
    APPLE_PUBLIC_KEY_NOT_INITIALIZED(50600, "Apple public key not initialized"),
    APPLE_PUBLIC_KEY_UNAVAILABLE(50700, "Invalid response from Apple during public key initialization"),
    APPLE_SIGNIN_PUBLIC_KEY_ERROR(50800,"Error during initializing public key");


    private String message;
    private Integer statusCode;

    AppleTokenStatus(int errorId, String errorMsg) {
        this.message = errorMsg;
        this.statusCode = errorId;
    }

    public String getErrorMsg() {
        return message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

}