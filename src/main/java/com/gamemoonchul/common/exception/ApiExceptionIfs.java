package com.gamemoonchul.common.exception;

import com.gamemoonchul.common.status.ApiStatusIfs;

public interface ApiExceptionIfs {
    ApiStatusIfs getStatus();

    String getDetail();

    Integer getHttpStatus();
}
