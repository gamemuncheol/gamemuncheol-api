package com.gamemoonchul.domain.entity.redis;

import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.config.oauth.user.OAuth2UserInfo;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "redis-member", timeToLive = 1800L) // 30분간 살아 있음
public class RedisMember {
    @Id
    private Long id;
    @Indexed
    @Builder.Default
    private String uniqueKey = UUID.randomUUID().toString();
    private String name;
    private OAuth2Provider provider;
    private String identifier;
    private String nickname;
    private String email;
    private String picture;
    private LocalDateTime birth;

    public RedisMember(OAuth2UserInfo userInfo) {
        this.name = userInfo.getName();
        this.provider = userInfo.getProvider();
        this.identifier = userInfo.getIdentifier();
        this.nickname = userInfo.getNickname();
        this.birth = userInfo.getBirth();
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
