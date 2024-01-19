package com.gamemoonchul.common.api;

import com.gamemoonchul.common.error.ErrorCodeIfs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ApiResult<T> {
  private final boolean success;
  private final T body;
  private final ErrorCodeIfs apiError;

  public static ApiResult OK() {
    return new ApiResult(
        true,
        null,
        null
    );
  }

  public static ApiResult ERROR(ErrorCodeIfs errorCode) {
    return new ApiResult(
        false,
        null,
        errorCode
    );
  }
}
