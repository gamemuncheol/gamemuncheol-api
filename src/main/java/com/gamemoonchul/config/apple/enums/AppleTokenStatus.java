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

// Modified by rookedsysc
    @Getter
public enum AppleTokenStatus implements ApiStatusIfs {

    // ID Token related errors
    INVALID_ID_TOKEN_SIGNATURE(50100, "ID Token signature verification failed"),
    INVALID_ID_TOKEN(50200,"Invalid ID Token"),
    EXPIRED_ID_TOKEN(50300, "Expired ID Token"),
    UNSUPPORTED_ID_TOKEN(50400, "Unsupported exception in Apple ID Token validation"),

    // Apple specific errors
    PROXY_SETUP_ERROR(60100, "Invalid proxy host and/or port"),
    APPLE_PUBLIC_KEY_NOT_INITIALIZED(60200, "Apple public key not initialized"),
    APPLE_PUBLIC_KEY_UNAVAILABLE(60300, "Invalid response from Apple during public key initialization"),
    APPLE_SIGNIN_PUBLIC_KEY_ERROR(60400,"Error during initializing public key");


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