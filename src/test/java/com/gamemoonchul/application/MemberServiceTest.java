package com.gamemoonchul.application;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.MemberDummy;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원 중복된 값 있어도 한 번만 생성되는지 테스트 ")
    void alreadyExistMember() {
        // given
        Member member = MemberDummy.create();
        memberRepository.save(member);

        // when
        memberService.signInOrUp(member);
        memberService.signInOrUp(member);
        memberService.signInOrUp(member);
        List<Member>
                members = memberRepository.findAllByEmailAndProviderAndIdentifier(member.getEmail(), member.getProvider(), member.getIdentifier());

        // then
        assertThat(members.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("닉네임 변경이 정상적으로 되는지 테스트")
    void updateNickName() {
        // given
        Member member = MemberDummy.create();
        memberRepository.save(member);
        String nickname = "우하하";

        // when
        memberService.updateNickNameOrThrow(member, nickname);
        List<Member> savedMember = memberRepository.findByNickname(nickname);

        // then
        assertThat(savedMember.size()).isEqualTo(1);
        assertThat(savedMember.get(0).getNickname()).isEqualTo(nickname);
    }

    @Test
    @DisplayName("동일한 닉네임으로 두 번 저장하면 예외 발생하는지 테스트")
    void alreadyExistNickName() {
        // given
        Member member = MemberDummy.create();
        memberRepository.save(member);
        String nickname = "우하하";

        // when
        memberService.updateNickNameOrThrow(member, nickname);

        // then
        assertThatThrownBy(() -> memberService.updateNickNameOrThrow(member, nickname))
                .isInstanceOf(ApiException.class)
                .hasMessageContaining(MemberStatus.ALREADY_EXIST_NICKNAME.getMessage());
    }
}