package com.gamemoonchul.common.exceptionHandler;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.common.status.ApiStatus;
import com.gamemoonchul.infrastructure.web.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ApiResponse> apiException(
            ApiException apiException
    ) {
        log.error("", apiException);

        int customErrorCode = apiException.getStatus().getStatusCode();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if ((customErrorCode % 1000) / 100 == 4) {
            httpStatus = HttpStatus.BAD_REQUEST;
        }

        return ResponseEntity.status(httpStatus)
                .body(ApiResponse.ERROR(apiException.getStatus()));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException e) {
        log.error("Unexpected Runtime Exception occurred: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.ERROR(ApiStatus.SERVER_ERROR));
    }
}
