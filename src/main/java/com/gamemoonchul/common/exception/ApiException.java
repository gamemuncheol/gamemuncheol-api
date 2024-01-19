package com.gamemoonchul.common.exception;

import com.gamemoonchul.common.error.ApiStatusIfs;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
  private final ApiStatusIfs errorCode;

  public ApiException(ApiStatusIfs errorCode) {
    super(errorCode.getStatusInfo());
    this.errorCode = errorCode;
  }
}
