package com.gamemoonchul.infrastructure.web;

import com.gamemoonchul.MemberOpenApiService;
import com.gamemoonchul.TestDataBase;
import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.MemberDummy;
import com.gamemoonchul.domain.entity.redis.RedisMember;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.repository.redis.RedisMemberRepository;
import com.gamemoonchul.infrastructure.web.dto.request.RegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


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
        RegisterRequest request = new RegisterRequest(redisMember.getUniqueKey(), true, "김문철");

        // when
        memberService.register(request);

        // then
        assertThat(redisMemberRepository.findRedisMemberByUniqueKey(redisMember.getUniqueKey())
                .isPresent()).isFalse();
        assertThat(memberRepository.findTop1ByProviderAndIdentifier(redisMember.getProvider(), redisMember.getIdentifier())).isNotNull();
    }

    @Test
    @DisplayName("중복된 닉네임이 있을 경우 register Exception 발생하는지 테스트")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void shouldThrowExceptionWhenNicknameIsDuplicate() {
        // given
        RedisMember redisMember1 = MemberDummy.createRedisMember("hong");
        RedisMember redisMember2 = MemberDummy.createRedisMember("jong");
        redisMember1 = redisMemberRepository.save(redisMember1);
        redisMember2 = redisMemberRepository.save(redisMember2);
        RegisterRequest request1 = new RegisterRequest(redisMember1.getUniqueKey(), true, "김문철");
        RegisterRequest request2 = new RegisterRequest(redisMember2.getUniqueKey(), true, "김문철");
        memberService.register(request1);

        // when // then
        assertThatThrownBy(() -> memberService.register(request2)
        )
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(MemberStatus.ALREADY_EXIST_NICKNAME.getMessage());
    }


    @Test
    @DisplayName("validate 메서드에서 이미 존재하는 닉네임을 입력했을 때 true를 return 하는지 Test")
    void validateNicknameTest() {
        // given
        Member member = MemberDummy.create();
        memberRepository.save(member);

        // when
        boolean result = memberService.isExistNickname(member.getNickname());

        // then
        assertThat(result).isEqualTo(true);
    }
}