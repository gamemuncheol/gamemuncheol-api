package com.gamemoonchul.common.exceptionHandler;

import com.gamemoonchul.common.api.ApiResult;
import com.gamemoonchul.common.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
public class ApiExceptionHandler {
  @ExceptionHandler(value = ApiException.class)
  public ResponseEntity<ApiResult> apiException(
      ApiException apiException
  ) {
    log.error("", apiException);

    var errorCode = apiException.getErrorCode();

    return ResponseEntity.status(errorCode.getHttpStatusCode())
        .body(ApiResult.ERROR(errorCode));
  }
}
