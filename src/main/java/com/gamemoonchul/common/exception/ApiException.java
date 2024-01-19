package com.gamemoonchul.common.exception;

import com.gamemoonchul.common.error.ErrorCodeIfs;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
  private final ErrorCodeIfs errorCode;

  public ApiException(ErrorCodeIfs errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
