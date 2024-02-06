package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.infrastructure.web.common.RestControllerWithEnvelopPattern;
import com.gamemoonchul.infrastructure.web.common.dto.ApiTest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@RequestMapping("/test")
@RestControllerWithEnvelopPattern
public class ApiTestController {

  private final ApiTestService apiTestService;

  @GetMapping("/{bool}")
  public ApiTest hello(
      @PathVariable boolean bool
  ) {
    return apiTestService.hello(bool);
  }
}
