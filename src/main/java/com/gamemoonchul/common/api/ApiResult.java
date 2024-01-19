package com.gamemoonchul.common.api;

import com.gamemoonchul.common.error.ApiStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResult<T> {
  private final boolean success;
  private final T body;
  private final ApiStatusIfs errorCodeInfo;

  public ApiResult(boolean success, T body, com.gamemoonchul.common.error.ApiStatusIfs errorCodeIfs) {
    this.success = success;
    this.body = body;
    this.errorCodeInfo = new ApiStatusIfs(errorCodeIfs);
  }

  public static ApiResult OK() {
    return new ApiResult(
        true,
        null,
        ApiStatus.OK
    );
  }

  public static ApiResult ERROR(com.gamemoonchul.common.error.ApiStatusIfs errorCode) {
    return new ApiResult(
        false,
        null,
        errorCode
    );
  }

  @Getter
  @AllArgsConstructor
  public static class ApiStatusIfs {
    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String message;

    public ApiStatusIfs(com.gamemoonchul.common.error.ApiStatusIfs errorCodeIfs) {
      this.httpStatusCode = errorCodeIfs.getHttpStatusCode();
      this.errorCode = errorCodeIfs.getErrorCode();
      this.message = errorCodeIfs.getStatusInfo();
    }
  }
}
