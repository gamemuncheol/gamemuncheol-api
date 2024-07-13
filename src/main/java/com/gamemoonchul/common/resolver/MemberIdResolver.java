package com.gamemoonchul.common.resolver;

import com.gamemoonchul.common.annotation.MemberId;
import com.gamemoonchul.config.jwt.JwtAuthorizationFilter;
import com.gamemoonchul.config.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberIdResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 어노테이션과 파라미터 타입 체크
        return parameter.hasParameterAnnotation(MemberId.class) && Long.class.equals(parameter.getParameterType());
    }

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        return getTokenInfo()
                .map(TokenInfo::id)
                .orElse(null);
    }

    private Optional<TokenInfo> getTokenInfo() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(attributes -> (TokenInfo) attributes.getAttribute(JwtAuthorizationFilter.TOKENINFO_ATTRIBUTE_NAME, RequestAttributes.SCOPE_REQUEST));
    }
}
