package com.gamemoonchul.common.api;

import com.gamemoonchul.common.error.ApiStatus;
import com.gamemoonchul.common.error.ApiStatusIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResult<T> {
  private final boolean success;
  private final T body;
  private final ApiStatusInfo statusInfo;

  public ApiResult(boolean success, T body, ApiStatusIfs errorCodeIfs) {
    this.success = success;
    this.body = body;
    this.statusInfo = new ApiStatusInfo(errorCodeIfs);
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
  public static class ApiStatusInfo {
    private final Integer httpStatusCode;
    private final Integer apiStatusCode;
    private final String message;

    public ApiStatusInfo(ApiStatusIfs errorCodeIfs) {
      this.httpStatusCode = errorCodeIfs.getHttpStatusCode();
      this.apiStatusCode = errorCodeIfs.getApiStatusCode();
      this.message = errorCodeIfs.getStatusInfo();
    }
  }
}
