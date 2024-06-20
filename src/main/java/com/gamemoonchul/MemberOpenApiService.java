package com.gamemoonchul;

import com.gamemoonchul.application.RedisMemberService;
import com.gamemoonchul.application.converter.MemberConverter;
import com.gamemoonchul.application.validation.ValidNickname;
import com.gamemoonchul.common.exception.BadRequestException;
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
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
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

    public boolean isExistNickname(@ValidNickname String nickName) {
        Optional<Member> savedMember = memberRepository.findByNickname(nickName);
        return savedMember.isPresent();
    }

    public TokenDto register(@Valid RegisterRequest request) {
        if (!request.privacyAgree()) {
            throw new UnauthorizedException(MemberStatus.CONSENT_REQUIRED);
        }
        if (isExistNickname(request.nickname())) {
            throw new BadRequestException(MemberStatus.ALREADY_EXIST_NICKNAME);
        }

        RedisMember redisMember = redisMemberService.findByTemporaryKey(request.temporaryKey());
        redisMember.setNickname(request.nickname());


        Member member = MemberConverter.redisMemberToEntity(redisMember);
        memberRepository.save(member);
        redisMemberService.delete(redisMember);

        TokenDto token = tokenHelper.generateToken(member);

        return token;
    }
}
