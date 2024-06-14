package com.gamemoonchul;

import com.gamemoonchul.domain.entity.MemberDummy;
import com.gamemoonchul.domain.entity.redis.RedisMember;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.repository.redis.RedisMemberRepository;
import com.gamemoonchul.infrastructure.web.dto.RegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class MemberOpenApiServiceTest extends TestDataBase {
    @Autowired
    private RedisMemberRepository redisMemberRepository;

    @Autowired
    private MemberOpenApiService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("redis member를 통해서 register 잘 되는지 확인")
    void registerTestWithUniqueKey() {
        // given
        RedisMember redisMember = MemberDummy.createRedisMember("hong");
        redisMember = redisMemberRepository.save(redisMember);
        RegisterRequest request = new RegisterRequest(redisMember.getUniqueKey(), true);

        // when
        memberService.register(request);

        // then
        assertThat(redisMemberRepository.findRedisMemberByUniqueKey(redisMember.getUniqueKey()).isPresent()).isFalse();
        assertThat(memberRepository.findTop1ByProviderAndIdentifier(redisMember.getProvider(), redisMember.getIdentifier())).isNotNull();
    }
}