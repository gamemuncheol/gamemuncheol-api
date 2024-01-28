package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.common.status.ApiStatus;
import com.gamemoonchul.infrastructure.web.common.dto.ApiTest;
import org.springframework.stereotype.Service;

@Service
public class ApiTestService {
  public ApiTest hello(boolean bool) {
    if (bool) {
      return ApiTest.mock();
    }
    throw new ApiException(ApiStatus.BAD_REQUEST);
  }
}
