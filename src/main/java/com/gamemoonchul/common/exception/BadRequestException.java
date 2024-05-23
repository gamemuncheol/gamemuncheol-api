package com.gamemoonchul.common.exception;

import com.gamemoonchul.common.status.ApiStatusIfs;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException implements ApiExceptionIfs {
  /***
   * {
   *   "status" : {
   *     "statusCode" : 400,
   *     "message" : "Bad Request"
   *   },
   *   "detail" : "파라미터의 인자값이 잘못되었습니다."
   * }
   */
  private final ApiStatusIfs status;
  // 좀 더 세부적인 Error Detail이 필요할 경우
  private final String detail;

  public BadRequestException(ApiStatusIfs status) {
    super(status.getMessage());
    this.status = status;
    this.detail = status.getMessage();
  }
}
