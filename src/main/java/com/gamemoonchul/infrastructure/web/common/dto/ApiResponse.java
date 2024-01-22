package com.gamemoonchul.infrastructure.web.common.dto;

import com.gamemoonchul.common.BusinessErrorCode;

public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;

    protected ApiResponse() {
    }

    public ApiResponse(BusinessErrorCode businessErrorCode) {
        this.code = businessErrorCode.getCode();
        this.message = businessErrorCode.getMessage();
    }

    public ApiResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
