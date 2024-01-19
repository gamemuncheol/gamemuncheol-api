package com.gamemoonchul.common.error;

public interface ApiStatusIfs {
  public Integer getHttpStatusCode();
  public Integer getErrorCode();
  public String getStatusInfo();
}
