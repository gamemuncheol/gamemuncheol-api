package com.gamemoonchul.infrastructure.adapter;

import com.gamemoonchul.application.ports.output.RiotApiPort;
import com.gamemoonchul.common.constants.Resilience4jConstants;
import com.gamemoonchul.common.exception.InternalServerException;
import com.gamemoonchul.domain.model.vo.riot.AccountRecord;
import com.gamemoonchul.domain.model.vo.riot.MatchRecord;
import com.gamemoonchul.domain.status.SearchStatus;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

@Slf4j
@Service
public class RiotApiAdapter implements RiotApiPort {
    private final RateLimiter shortTermLimiter;
    private final RateLimiter longTermLimiter;

    private final RestTemplate restTemplate;

    @Value("${lol.api.key}")
    private String apiKey;


    @Autowired
    public RiotApiAdapter(@Qualifier(Resilience4jConstants.SHORT_TERM_RIOT_API_RATE_LIMITER) RateLimiter shortTermLimiter, @Qualifier(Resilience4jConstants.LONG_TERM_RIOT_API_RATE_LIMITER) RateLimiter longTermBreaker, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.shortTermLimiter = shortTermLimiter;
        this.longTermLimiter = longTermBreaker;
    }

    // 유저 검색 API
    @Deprecated
    public AccountRecord searchUser(String gameName, String tagLine) {
        AccountRecord result = restTemplate.getForObject("https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + gameName + "/" + tagLine + "?api_key=" + apiKey, AccountRecord.class);

        return result;
    }

    /**
     * User의 게임 목록을 가져옴
     *
     * @param matchId
     * @return
     */
    @Override
    public MatchRecord searchMatch(String matchId) {
        try {
            return executeWithRateLimiting(() -> {
                String url = "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchId + "?api_key=" + apiKey;
                log.info("요청 보낸시간 : " + System.currentTimeMillis());
                return restTemplate.getForObject(url, MatchRecord.class);
            });
        } catch (RequestNotPermitted e) {
            log.warn("Rate limit exceeded: {}", e.getMessage());
            return rateLimiterFallback(e, SearchStatus.RIOT_API_RATE_LIMIT_EXCEED);
        } catch (Exception e) {
            log.error("Riot API 호출 실패: {}", e.getMessage());
            return rateLimiterFallback(e, SearchStatus.RIOT_API_UNKNOWN_EXCEPTION);
        }
    }

    private <T> T executeWithRateLimiting(Supplier<T> supplier) throws Exception {
        if (!this.shortTermLimiter.acquirePermission()) {
            log.warn("Short-term rate limit reached");
            throw RequestNotPermitted.createRequestNotPermitted(shortTermLimiter);
        }
        if (!longTermLimiter.acquirePermission()) {
            log.warn("Long-term rate limit reached");
            throw RequestNotPermitted.createRequestNotPermitted(longTermLimiter);
        }
        return supplier.get();
    }

    private MatchRecord rateLimiterFallback(Exception e, SearchStatus status) {
        throw new InternalServerException(status);
    }
}
