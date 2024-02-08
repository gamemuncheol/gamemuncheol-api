package com.gamemoonchul.infrastructure.repository;


import com.gamemoonchul.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository  extends JpaRepository<MemberEntity, Long> {
  Optional<MemberEntity> findTop1ByEmail(String email);
  List<MemberEntity> findByEmail(String email);
}
