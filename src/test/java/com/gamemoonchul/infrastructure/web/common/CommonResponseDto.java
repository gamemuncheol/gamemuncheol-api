package com.gamemoonchul.infrastructure.web.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommonResponseDto<T> {
    private boolean success;
    private Status status;
    private T data;

    @AllArgsConstructor
    class Status {
        private String statusCode;
        private String message;
    }
}
