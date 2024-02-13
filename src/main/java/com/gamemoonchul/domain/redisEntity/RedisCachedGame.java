package com.gamemoonchul.domain.redisEntity;

import com.gamemoonchul.domain.dto.SearchedUser;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@RedisHash(value = "redishash-game", timeToLive = 60 * 60 * 6) // 6시간
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder @Getter
public class RedisCachedGame {
    @Id
    @Indexed
    private Long id;
    @Indexed
    private String searchKeyword;
    private String gametype;
    private String gamedate;
    private String gameresult;
    private String gametime;
    private String img_src;
    private List<SearchedUser> our_team;
    private List<SearchedUser> enemy_team;
}
