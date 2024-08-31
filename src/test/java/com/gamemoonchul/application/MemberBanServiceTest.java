package com.gamemoonchul.application;

import com.gamemoonchul.TestDataBase;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.MemberBan;
import com.gamemoonchul.domain.entity.MemberDummy;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberBanServiceTest extends TestDataBase {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberBanService memberBanService;

    @Test
    @DisplayName("Member Ban 후 Member Ban List 잘 받아와지는지 테스트")
    void banTest() {
        // given
        Member member = MemberDummy.createWithId("홍길동");
        Member banMember = MemberDummy.createWithId("김철수");
        member = memberRepository.save(member);
        banMember = memberRepository.save(banMember);

        // when
        memberBanService.ban(member, banMember.getId());

        // then
        List<MemberBan> bannedMember = memberBanService.bannedMembers(member.getId());
        assertEquals(1, bannedMember.size());
        assertEquals(bannedMember.get(0).getBanMember().getName(), banMember.getName());
    }

    @Test
    @DisplayName("Member Ban 후 Ban 갯수 늘어났는지 테스트")
    void banCountTest() {
        // given
        Member member = MemberDummy.createWithId("홍길동");
        Member banMember = MemberDummy.createWithId("김철수");
        member = memberRepository.save(member);
        banMember = memberRepository.save(banMember);

        // when
        memberBanService.ban(member, banMember.getId());

        // then
        Integer banCnt = memberBanService.countByMemberId(member.getId());
        assertEquals(1, banCnt);
    }
}