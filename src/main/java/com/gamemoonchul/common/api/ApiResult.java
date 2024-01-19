package com.gamemoonchul.common.api;

import com.gamemoonchul.common.error.ErrorCode;
import com.gamemoonchul.common.error.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResult<T> {
  private final boolean success;
  private final T body;
  private final ErrorCodeInfo errorCodeInfo;

  public ApiResult(boolean success, T body, ErrorCodeIfs errorCodeIfs) {
    this.success = success;
    this.body = body;
    this.errorCodeInfo = new ErrorCodeInfo(errorCodeIfs);
  }

  public static ApiResult OK() {
    return new ApiResult(
        true,
        null,
        ErrorCode.OK
    );
  }

  public static ApiResult ERROR(ErrorCodeIfs errorCode) {
    return new ApiResult(
        false,
        null,
        errorCode
    );
  }

  @Getter
  @AllArgsConstructor
  public static class ErrorCodeInfo {
    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String message;

    public ErrorCodeInfo(ErrorCodeIfs errorCodeIfs) {
      this.httpStatusCode = errorCodeIfs.getHttpStatusCode();
      this.errorCode = errorCodeIfs.getErrorCode();
      this.message = errorCodeIfs.getMessage();
    }
  }
}
