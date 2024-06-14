package com.gamemoonchul;

import com.gamemoonchul.application.RedisMemberService;
import com.gamemoonchul.application.converter.MemberConverter;
import com.gamemoonchul.common.exception.UnauthorizedException;
import com.gamemoonchul.config.jwt.TokenDto;
import com.gamemoonchul.config.jwt.TokenHelper;
import com.gamemoonchul.config.jwt.TokenInfo;
import com.gamemoonchul.config.jwt.TokenType;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.redis.RedisMember;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.web.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberOpenApiService {
    private final TokenHelper tokenHelper;
    private final RedisMemberService redisMemberService;
    private final MemberRepository memberRepository;
    public TokenDto renew(String refreshToken) {
        tokenHelper.validateToken(refreshToken, TokenType.REFRESH);
        TokenInfo tokenInfo = tokenHelper.getTokenInfo(refreshToken);
        TokenDto newToken = tokenHelper.generateToken(tokenInfo);
        return newToken;
    }

    public TokenDto register(RegisterRequest request) {
        if (!request.privacyAgree()) {
            throw new UnauthorizedException(MemberStatus.CONSENT_REQUIRED);
        }
        RedisMember redisMember = redisMemberService.findByUniqueKey(request.temporaryKey());

        Member member = MemberConverter.redisMemberToEntity(redisMember);
        memberRepository.save(member);
        redisMemberService.delete(redisMember);

        TokenDto token = tokenHelper.generateToken(member);

        return token;
    }
}
