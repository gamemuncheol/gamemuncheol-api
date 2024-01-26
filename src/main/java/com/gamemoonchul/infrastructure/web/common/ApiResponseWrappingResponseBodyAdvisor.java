package com.gamemoonchul.infrastructure.web.common;

import com.gamemoonchul.common.util.JsonUtils;
import com.gamemoonchul.infrastructure.web.common.dto.ApiResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * 웹요청 처리결과 응답값을 봉투패턴(Envelop pattern)으로 일정한 데이터 형식으로 가공
 *
 * <pre>
 * {
 *     "code": "0000",
 *     "message": "OK",
 *     "data": { data }
 * }
 * </pre>
 */
@RestControllerAdvice(annotations = {RestControllerWithEnvelopPattern.class})
public class ApiResponseWrappingResponseBodyAdvisor implements ResponseBodyAdvice<Object> {
    private static final Logger LOG =
            LoggerFactory.getLogger(ApiResponseWrappingResponseBodyAdvisor.class);

    private Type getGenericType(MethodParameter returnType) {

        if (HttpEntity.class.isAssignableFrom(returnType.getParameterType())) {
            return ResolvableType.forType(returnType.getGenericParameterType())
                    .getGeneric()
                    .getType();
        } else {
            return returnType.getGenericParameterType();
        }
    }

    @Override
    public boolean supports(
            MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Type type =
                GenericTypeResolver.resolveType(
                        getGenericType(returnType), returnType.getContainingClass());

        if (returnType.getExecutable().getName().toUpperCase().contains("HEALTH")) { // healthCheck
            return false;
        }

        if (Void.class.getName().equals(type.getTypeName())) {
            return false;
        }

        return !converterType.isAssignableFrom(ByteArrayHttpMessageConverter.class)
                && !converterType.isAssignableFrom(ResourceHttpMessageConverter.class);
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {

        HttpStatus responseStatus =
                HttpStatus.valueOf(
                        ((ServletServerHttpResponse) response).getServletResponse().getStatus());

        if (Objects.isNull(body)) {
            return responseStatus.isError()
                    ? ApiResponseGenerator.fail()
                    : ApiResponseGenerator.success();
        }

        var apiResponse =
                responseStatus.isError()
                        ? ApiResponseGenerator.fail(body)
                        : ApiResponseGenerator.success(body);
        LOG.trace("[ApiResponse] {}", apiResponse);

        if (selectedConverterType.isAssignableFrom(StringHttpMessageConverter.class)) {
            try {
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return JsonUtils.toJson(apiResponse);
            } catch (JsonUtils.JsonEncodeException jpe) {
                throw new ApiResponseJsonProcessingException(jpe);
            }
        }

        return apiResponse;
    }
}
