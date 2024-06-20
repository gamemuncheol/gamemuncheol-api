package com.gamemoonchul.domain.entity.redis;

import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import jakarta.persistence.*;
import lombok.*;
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
