package com.gamemoonchul.common.exception;

import com.gamemoonchul.common.status.ApiStatusIfs;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException implements ApiExceptionIfs {
  /***
   * {
   *   "status" : {
   *     "statusCode" : 404,
   *     "message" : "Not Found"
   *   },
   *   "detail" : "Not Found"
   * }
   */
  private final ApiStatusIfs status;
  private final String detail;
  private final Integer httpStatus;

  public NotFoundException(ApiStatusIfs status) {
    super(status.getMessage());
    this.status = status;
    this.detail = status.getMessage();
    this.httpStatus = HttpStatus.NOT_FOUND.value();
  }
}
