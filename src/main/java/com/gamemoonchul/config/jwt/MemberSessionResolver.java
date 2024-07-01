package com.gamemoonchul.config.jwt;

import com.gamemoonchul.common.annotation.MemberSession;
import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
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
public class MemberSessionResolver implements HandlerMethodArgumentResolver {
    private final MemberRepository memberRepository;

    @Override
    /// 여기서 True로 return이 되면 resolveArgument가 실행됨
    public boolean supportsParameter(MethodParameter parameter) {
        // 지원하는 파라미터 체크, 어노테이션 체크하는 영역
        // 1. 어노테이션이 있는지 체크
        var annotation = parameter.hasParameterAnnotation(MemberSession.class);
        // 2. parameter type 체크
        boolean parameterType = parameter.getParameterType()
                .equals(Member.class);

        return annotation && parameterType;
    }

    @Override
    @Nullable
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        return getTokenInfo()
                .map(this::findMember)
                .orElse(null);
    }

    private Optional<TokenInfo> getTokenInfo() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(attributes -> (TokenInfo) attributes.getAttribute("tokenInfo", RequestAttributes.SCOPE_REQUEST));
    }

    private Member findMember(TokenInfo tokenInfo) {
        return memberRepository.findTop1ByProviderAndIdentifier(
                        OAuth2Provider.valueOf(tokenInfo.provider()), tokenInfo.identifier())
                .orElseThrow(() -> {
                    log.error(MemberStatus.MEMBER_NOT_FOUND.getMessage());
                    return new BadRequestException(MemberStatus.MEMBER_NOT_FOUND);
                });
    }
}
