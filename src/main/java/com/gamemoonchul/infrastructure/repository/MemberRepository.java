package com.gamemoonchul.infrastructure.repository;


import com.gamemoonchul.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository  extends JpaRepository<MemberEntity, Long> {
  Optional<MemberEntity> findTop1ByEmail(String email);
}
