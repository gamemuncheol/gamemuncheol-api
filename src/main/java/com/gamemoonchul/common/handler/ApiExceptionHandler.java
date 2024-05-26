package com.gamemoonchul.common.handler;

import com.gamemoonchul.common.exception.*;
import com.gamemoonchul.infrastructure.web.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiResponse> badRequest(
            Exception ex, ApiExceptionIfs apiException
    ) {
        return ResponseEntity.status(apiException.getHttpStatus())
                .body(ApiResponse.ERROR(apiException.getStatus()));
    }
}
