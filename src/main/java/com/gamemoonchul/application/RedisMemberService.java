package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.UnauthorizedException;
import com.gamemoonchul.domain.entity.redis.RedisMember;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.infrastructure.repository.redis.RedisMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisMemberService {
    private final RedisMemberRepository memberRepository;

    public RedisMember findByTemporaryKey(String uniqueKey) {
        return memberRepository.findRedisMemberByUniqueKey(uniqueKey)
                .orElseThrow(() -> new UnauthorizedException(MemberStatus.EXPIRED_KEY));
    }

    public void delete(RedisMember redisMember) {
        memberRepository.delete(redisMember);
    }
}
