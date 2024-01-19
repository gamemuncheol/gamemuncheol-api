package com.gamemoonchul.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode implements ErrorCodeIfs {
  OK(200,200, "OK"),
  BAD_REQUEST(400,400, "Wrong Request"),
    ;


  private final Integer httpStatusCode;
  private final Integer errorCode;
  private final String message;

}
