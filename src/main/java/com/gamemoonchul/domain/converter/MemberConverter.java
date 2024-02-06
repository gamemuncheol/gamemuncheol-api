package com.gamemoonchul.domain.converter;

import com.gamemoonchul.config.apple.entities.AppleUserInfo;
import com.gamemoonchul.config.oauth.user.OAuth2UserInfo;
import com.gamemoonchul.domain.MemberEntity;
import com.gamemoonchul.domain.enums.MemberRole;

import java.util.Optional;

public class MemberConverter {
  public static MemberEntity toEntity(OAuth2UserInfo userInfo) {
    Optional<String> nickname = Optional.ofNullable(userInfo
        .getNickname());
    if (nickname.isEmpty()) {
      nickname = Optional.ofNullable(userInfo
          .getId());
    }
    MemberEntity member = MemberEntity.builder()
        .role(MemberRole.USER)
        .name(userInfo.getName())
        .nickname(
            nickname.get()
        )
        .score(0.0)
        .email(userInfo
            .getEmail())
        .picture(userInfo
            .getProfileImageUrl())
        .birth(null)
        .build();
    return member;
  }

  public static MemberEntity toEntity(AppleUserInfo userInfo) {
    MemberEntity member = MemberEntity.builder()
        .role(MemberRole.USER)
        .name(userInfo.getName())
        .nickname(userInfo.getClientId())
        .score(0.0)
        .email(userInfo.getEmail())
        .picture(null)
        .birth(null)
        .build();
    return member;
  }
}
