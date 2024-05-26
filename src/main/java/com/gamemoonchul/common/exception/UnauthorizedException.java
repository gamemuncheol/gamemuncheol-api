package com.gamemoonchul.common.exception;

import com.gamemoonchul.common.status.ApiStatusIfs;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException implements ApiExceptionIfs {
  /***
   * {
   *   "status" : {
   *     "statusCode" : 401,
   *     "message" : "Unauthorized"
   *   },
   *   "detail" : "권한이 필요합니다."
   * }
   */
  private final ApiStatusIfs status;
  private final String detail;
  private final Integer httpStatus;

  public UnauthorizedException(ApiStatusIfs status) {
    super(status.getMessage());
    this.status = status;
    this.detail = status.getMessage();
    this.httpStatus = HttpStatus.UNAUTHORIZED.value();
  }
}
