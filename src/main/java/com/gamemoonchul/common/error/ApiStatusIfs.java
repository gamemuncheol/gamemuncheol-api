package com.gamemoonchul.common.error;

public interface ApiStatusIfs {
  public Integer getHttpStatusCode();
  public Integer getApiStatusCode();
  public String getStatusInfo();
}
