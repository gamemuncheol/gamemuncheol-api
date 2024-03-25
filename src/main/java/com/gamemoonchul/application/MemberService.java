package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.config.jwt.TokenDto;
import com.gamemoonchul.config.jwt.TokenHelper;
import com.gamemoonchul.config.jwt.TokenInfo;
import com.gamemoonchul.config.jwt.TokenType;
import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.domain.converter.MemberConverter;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.web.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final TokenHelper tokenHelper;
    private final MemberConverter memberConverter;

    public void signInOrUp(Member member) {
        Optional<Member> alreadyExistMember = memberRepository.findTop1ByEmailAndProviderAndIdentifier(member.getEmail(), member.getProvider(), member.getIdentifier());
        if (alreadyExistMember.isEmpty()) {
            memberRepository.save(member);
        } else {
            member = alreadyExistMember.get();
        }
    }

    public void unlink(String email, OAuth2Provider provider, String identifier) {
        Optional<Member> member = memberRepository.findTop1ByEmailAndProviderAndIdentifier(email, provider, identifier);
        if (member.isEmpty()) {
            throw new ApiException(MemberStatus.MEMBER_NOT_FOUND);
        }
        memberRepository.delete(member.get());
    }

    public void updateNickNameOrThrow(Member member, String nickName) {
        List<Member> savedMember = memberRepository.findByNickname(nickName);
        if (!savedMember.isEmpty()) {
            throw new ApiException(MemberStatus.ALREADY_EXIST_NICKNAME);
        }
        Member updatedMember = member.updateNickname(nickName);
        memberRepository.save(updatedMember);
    }

    public TokenDto renew(String refreshToken) {
        tokenHelper.validateToken(refreshToken, TokenType.REFRESH);
        TokenInfo tokenInfo = tokenHelper.getTokenInfo(refreshToken);
        TokenDto newToken = tokenHelper.generateToken(tokenInfo);
        return newToken;
    }

    public MemberResponseDto me(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new ApiException(MemberStatus.MEMBER_NOT_FOUND);
        }
        MemberResponseDto response = memberConverter.toResponseDto(member.get());

        return response;
    }
}
