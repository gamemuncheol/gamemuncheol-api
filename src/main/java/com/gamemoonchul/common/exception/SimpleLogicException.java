package com.gamemoonchul.common.exception;

import com.gamemoonchul.common.BusinessErrorCode;

public class SimpleLogicException extends BusinessException{

    public SimpleLogicException(String message) {
        super(message);
    }
    @Override
    public BusinessErrorCode getErrorCode() {
        return BusinessErrorCode.BAD_REQUEST;
    }

    @Override
    public boolean isNecessaryToLog() {
        return false;
    }
}
