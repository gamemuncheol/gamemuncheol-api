package com.gamemoonchul.infrastructure.web.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiTest {
  private String name;
  private int age;

  public static ApiTest mock() {
    return new ApiTest("김한글", 24);
  }
}
