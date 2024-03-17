package com.gamemoonchul.infrastructure.repository;


import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository  extends JpaRepository<Member, Long> {
  Optional<Member> findTop1ByEmailAndProvider(String email, OAuth2Provider issuer);
  List<Member> findByEmail(String email);
}
