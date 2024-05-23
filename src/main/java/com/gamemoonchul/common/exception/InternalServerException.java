package com.gamemoonchul.common.exception;

import com.gamemoonchul.common.status.ApiStatusIfs;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException implements ApiExceptionIfs {
  /***
   * {
   *   "status" : {
   *     "statusCode" : 500,
   *     "message" : "Bad Request"
   *   },
   *   "detail" : "알 수 없는 서버 오류"
   * }
   */
  private final ApiStatusIfs status;
  // 좀 더 세부적인 Error Detail이 필요할 경우
  private final String detail;

  public InternalServerException(ApiStatusIfs status) {
    super(status.getMessage());
    this.status = status;
    this.detail = status.getMessage();
  }
}
