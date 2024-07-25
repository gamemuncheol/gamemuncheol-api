package com.gamemoonchul.common.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiStatus implements ApiStatusIfs {
    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    SERVER_ERROR(500, "Server Error"),
    NULL_POINT(512, "Null Point Error"),
    NOT_FOUND(404, "API Not Found"),
    ;

    private final Integer statusCode;
    private final String message;
}
