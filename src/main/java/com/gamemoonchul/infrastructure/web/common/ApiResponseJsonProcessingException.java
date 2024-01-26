package com.gamemoonchul.infrastructure.web.common;

import com.gamemoonchul.common.BusinessErrorCode;
import com.gamemoonchul.common.exception.BusinessException;

public class ApiResponseJsonProcessingException extends BusinessException {

    public ApiResponseJsonProcessingException(Throwable cause) {
        super(cause);
    }

    @Override
    public BusinessErrorCode getErrorCode() {
        return BusinessErrorCode.INTERNAL_SERVER_ERROR;
    }

    @Override
    public boolean isNecessaryToLog() {
        return true;
    }
}
