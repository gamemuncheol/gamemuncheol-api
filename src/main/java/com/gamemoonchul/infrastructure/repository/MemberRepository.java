package com.gamemoonchul.infrastructure.repository;


import com.gamemoonchul.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository  extends JpaRepository<Member, Long> {
  Optional<Member> findTop1ByEmail(String email);
  List<Member> findByEmail(String email);
}
