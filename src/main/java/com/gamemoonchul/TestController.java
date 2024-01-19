package com.gamemoonchul;

import com.gamemoonchul.common.api.ApiResult;
import com.gamemoonchul.common.error.ApiStatus;
import com.gamemoonchul.common.exception.ApiException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
  @GetMapping("/hello/{bool}")
  public ApiResult test(
      @PathVariable(value = "bool") boolean bool
  ) {
    if (!bool) {
      throw new ApiException(ApiStatus.BAD_REQUEST);
    }
    return ApiResult.OK();
  }
}
