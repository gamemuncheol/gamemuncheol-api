package com.gamemoonchul.common.exception;

import com.gamemoonchul.common.BusinessErrorCode;
import com.gamemoonchul.common.util.JsonUtils;
import lombok.NonNull;

import java.util.Map;

public abstract class BusinessException extends RuntimeException {
    protected BusinessException() {
    }

    protected BusinessException(String message) {
        super(message);
    }

    protected BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    protected BusinessException(Throwable cause) {
        super(cause);
    }

    protected BusinessException(@NonNull Map<String, Object> messageFields) {
        super(JsonUtils.toJson(messageFields));
    }

    protected BusinessException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public abstract BusinessErrorCode getErrorCode();

    public abstract boolean isNecessaryToLog();
}
