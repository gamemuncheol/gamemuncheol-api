package com.gamemoonchul.config.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamemoonchul.common.status.ApiStatus;
import com.gamemoonchul.domain.status.Oauth2Status;
import com.gamemoonchul.infrastructure.web.common.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json");

        if (response.getStatus() == 404) {
            handleNotFoundException(response);
        } else {
            handleAuthException(response, authException);
        }
    }

    private void handleNotFoundException(HttpServletResponse response) throws IOException {
        objectMapper.writeValue(response.getWriter(), ApiResponse.ERROR(ApiStatus.NOT_FOUND, null));
    }

    private void handleAuthException(HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        Arrays.stream(authException.getStackTrace()).map(StackTraceElement::toString).forEach(log::error);
        log.error(authException.getMessage() + "\n" + Arrays.toString(authException.getStackTrace()));
        objectMapper.writeValue(response.getWriter(), ApiResponse.ERROR(Oauth2Status.LOGIN_FAILED, authException.getMessage()));
    }
}
