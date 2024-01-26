package com.gamemoonchul.infrastructure.web.common;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Optional;

@RestControllerAdvice(annotations = RestControllerWithEnvelopPattern.class)
public class ApiResponseWrappingResponseBodyAdvisor implements ResponseBodyAdvice<Object> {
  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    // 컨트롤러의 반환타입이 객체일 때는 직렬화를 위해 MappingJackson2HttpMessageConverter를 사용한다.
    // 따라서, MappingJackson2HttpMessageConverter를 사용하는 경우에만 beforeBodyWrite 메서드를 호출하도록 한다.
    return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
    if (Optional.ofNullable(body).isPresent()) {
      return ApiResponse.OK(body);
    }
    return ApiResponse.OK();
  }
}
