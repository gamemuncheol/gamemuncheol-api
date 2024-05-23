package com.gamemoonchul.common.exception;

import com.gamemoonchul.common.status.ApiStatusIfs;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class MethodNotAllowedException extends RuntimeException implements ApiExceptionIfs {
  /***
   * {
   *   "status" : {
   *     "statusCode" : 405,
   *     "message" : "Method Not Allowed"
   *   },
   *   "detail" : "Method Not Allowed"
   * }
   */
  private final ApiStatusIfs status;
  private final String detail;

  public MethodNotAllowedException(ApiStatusIfs status) {
    super(status.getMessage());
    this.status = status;
    this.detail = status.getMessage();
  }
}
