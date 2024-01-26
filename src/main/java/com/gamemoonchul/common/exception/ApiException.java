package com.gamemoonchul.common.exception;

import com.gamemoonchul.common.status.ApiStatusIfs;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException implements ApiExceptionIfs {
  /***
   * Custom하게 생성된 ApiStatus
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

  public ApiException(ApiStatusIfs status) {
    super(status.getMessage());
    this.status = status;
    this.detail = status.getMessage();
  }
}
