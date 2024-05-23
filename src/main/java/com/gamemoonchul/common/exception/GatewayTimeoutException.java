package com.gamemoonchul.common.exception;

import com.gamemoonchul.common.status.ApiStatusIfs;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
public class GatewayTimeoutException extends RuntimeException implements ApiExceptionIfs {
  /***
   * {
   *   "status" : {
   *     "statusCode" : 504,
   *     "message" : "Gateway Timeout"
   *   },
   *   "detail" : "Gateway Timeout"
   * }
   */
  private final ApiStatusIfs status;
  private final String detail;

  public GatewayTimeoutException(ApiStatusIfs status) {
    super(status.getMessage());
    this.status = status;
    this.detail = status.getMessage();
  }
}
