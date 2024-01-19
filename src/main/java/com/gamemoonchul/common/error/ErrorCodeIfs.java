package com.gamemoonchul.common.error;

public interface ErrorCodeIfs {
  public Integer getHttpStatusCode();
  public Integer getErrorCode();
  public String getMessage();
}
