package com.gamemoonchul.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@Component
public class LoggerFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    ContentCachingRequestWrapper req = new ContentCachingRequestWrapper((HttpServletRequest) request);
    ContentCachingResponseWrapper res = new ContentCachingResponseWrapper((HttpServletResponse) response);

    ///# Do Filter 기준으로 실행 전과 실행 후가 나뉜다.
    /// TODO: Header 정보와 Body 정보를 찍어주는게 좋음, 한 번 해볼 것
    chain.doFilter(req, res);

    // request 정보
    Enumeration<String> headerNames = req.getHeaderNames();
    StringBuilder headerValues = new StringBuilder();

    /// TODO: asIterator() 란?
    headerNames.asIterator().forEachRemaining(headerKey -> {
      String headerValue = req.getHeader(headerKey);
      // authorization-token : ??? , user-agent : ???
      headerValues
        .append("[")
        .append(headerKey)
        .append(" : ")
        .append(headerValue).append("]");
    });

    String requestBody = new String(req.getContentAsByteArray());
    String uri = req.getRequestURI();
    String method = req.getMethod();
    log.info(">>>>> uri : {} , method : {} , header : {} , body : {}", uri, method, headerValues, requestBody);

    StringBuilder responseHeaderValues = new StringBuilder();

    res.getHeaderNames().forEach((headerKey) -> {
      String headerValue = res.getHeader(headerKey);
      responseHeaderValues.append("[").append(headerKey).append(" : ").append(headerValue).append("]");
    });

    String responseBody = new String(res.getContentAsByteArray());
    log.info("<<<<< uri : {} , method : {} , header : {} , body : {}", uri, method, responseHeaderValues, responseBody);

    res.copyBodyToResponse();
  }
}