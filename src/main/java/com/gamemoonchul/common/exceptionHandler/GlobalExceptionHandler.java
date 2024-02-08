package com.gamemoonchul.common.exceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException e) {
        // 알 수 없는 에러 반환
        return ResponseEntity.status(500).body("An unknown error occurred.");
    }
}