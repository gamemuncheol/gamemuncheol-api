package com.gamemoonchul.application;

import com.gamemoonchul.TestDataBase;
import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.MemberDummy;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class MemberServiceTest extends TestDataBase {
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
        memberService.signIn(member);
        memberService.signIn(member);
        memberService.signIn(member);
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
        memberService.updateNickName(member, nickname);
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
        memberService.updateNickName(member, nickname);

        // then
        assertThatThrownBy(() -> memberService.updateNickName(member, nickname))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(MemberStatus.ALREADY_EXIST_NICKNAME.getMessage());
    }

    @Test
    @DisplayName("validate 메서드에서 이미 존재하는 닉네임을 입력했을 때 false를 return 하는지 Test")
    void validateNicknameTest() {
        // given
        Member member = MemberDummy.create();
        memberRepository.save(member);

        // when
        boolean result = memberService.checkDuplicated(member.getNickname());

        // then
        assertThat(result).isEqualTo(false);
    }
}