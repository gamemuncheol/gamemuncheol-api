package com.gamemoonchul.common.handler;

import com.gamemoonchul.common.exception.*;
import com.gamemoonchul.common.status.ApiStatus;
import com.gamemoonchul.infrastructure.web.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

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

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiResponse> validexception(
            MethodArgumentNotValidException exception
    ) {
        log.error("", exception);

        List<String> errorMessageList = exception.getFieldErrors()
                .stream()
                .map(
                        objectError -> {
                            String format = "%s : { %s } 은 %s";
                            return String.format(format, objectError.getField(), objectError.getRejectedValue(), objectError.getDefaultMessage());
                        })
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.ERROR(ApiStatus.BAD_REQUEST, errorMessageList));
    }
}
