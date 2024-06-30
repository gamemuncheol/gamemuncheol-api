package com.gamemoonchul.application.member;

import com.gamemoonchul.TestDataBase;
import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.domain.entity.Member;
import com.gamemoonchul.domain.entity.MemberDummy;
import com.gamemoonchul.domain.status.MemberStatus;
import com.gamemoonchul.infrastructure.repository.CommentRepository;
import com.gamemoonchul.infrastructure.repository.MemberRepository;
import com.gamemoonchul.infrastructure.repository.PostRepository;
import com.gamemoonchul.infrastructure.repository.VoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class MemberDeactivateServiceTest extends TestDataBase {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private MemberDeactivateService memberDeactivateService;

    @Test
    @DisplayName("계정 비활성화가 정상적으로 되는지 테스트")
    void deactivateAccount() {
        // given
        Member member = MemberDummy.create();
        memberRepository.save(member);
        String email = member.getEmail();
        OAuth2Provider provider = member.getProvider();
        String identifier = member.getIdentifier();

        // when
        memberDeactivateService.deactivateAccount(email, provider, identifier);

        // then
        Optional<Member> deletedMember = memberRepository.findTop1ByProviderAndIdentifier(provider, identifier);
        assertThat(deletedMember).isEmpty();

        assertThat(voteRepository.findByMember(member)).isEmpty();
        assertThat(postRepository.findByMember(member)).isEmpty();
        assertThat(commentRepository.findAllByMember(member)).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 계정 비활성화 시 예외 발생하는지 테스트")
    void deactivateNonExistentAccount() {
        // given
        String email = "nonexistent@example.com";
        OAuth2Provider provider = OAuth2Provider.GOOGLE; // 예시로 GOOGLE 사용
        String identifier = "nonexistent_identifier";

        // when & then
        assertThatThrownBy(() -> memberDeactivateService.deactivateAccount(email, provider, identifier))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(MemberStatus.MEMBER_NOT_FOUND.getMessage());
    }
}
