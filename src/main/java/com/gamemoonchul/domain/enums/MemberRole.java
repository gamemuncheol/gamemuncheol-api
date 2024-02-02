package com.gamemoonchul.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MemberRole {
  ADMIN("ROLE_ADMIN", "관리자"),
  USER("ROLE_USER","사용자"),
  ;
  private final String key;
  private final String description;
  public String getKey() {
    return key;
  }
}
