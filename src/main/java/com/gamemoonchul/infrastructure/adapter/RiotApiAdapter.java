package com.gamemoonchul.infrastructure.adapter;

import com.gamemoonchul.application.ports.output.RiotApiPort;
import com.gamemoonchul.common.exception.InternalServerException;
import com.gamemoonchul.domain.model.vo.riot.AccountRecord;
import com.gamemoonchul.domain.model.vo.riot.MatchRecord;
import com.gamemoonchul.domain.status.SearchStatus;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RiotApiAdapter implements RiotApiPort {
    private final CircuitBreaker shortTermBreaker;
    private final CircuitBreaker longTermBreaker;

    private final RestTemplate restTemplate;

    @Value("${lol.api.key}")
    private String apiKey;


    @Autowired
    public RiotApiAdapter(
        @Qualifier("shortTermCircuitBreaker") CircuitBreaker shortTermBreaker,
        @Qualifier("longTermCircuitBreaker") CircuitBreaker longTermBreaker,
        RestTemplate restTemplate
    ) {
        this.restTemplate = restTemplate;
        this.shortTermBreaker = shortTermBreaker;
        this.longTermBreaker = longTermBreaker;
    }

    // 유저 검색 API
    @Deprecated
    public AccountRecord searchUser(String gameName, String tagLine) {
        AccountRecord result = restTemplate.getForObject(
            "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + gameName + "/" + tagLine + "?api_key=" + apiKey,
            AccountRecord.class
        );

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
            // CircuitBreaker로 보호된 호출
            return CircuitBreaker.decorateSupplier(shortTermBreaker,
                () -> CircuitBreaker.decorateSupplier(longTermBreaker,
                    () -> restTemplate.getForObject(
                        "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchId + "?api_key=" + apiKey,
                        MatchRecord.class
                    )
                ).get()
            ).get();
        } catch (Exception e) {
            // 예외 발생 시 Fallback 처리
            return circuitFallBack(matchId, e);
        }
    }

    // Fallback 메서드
    public MatchRecord circuitFallBack(String matchId, Throwable throwable) {
        log.error("Riot API 호출 실패: {}", throwable.getMessage());
        throw new InternalServerException(SearchStatus.RIOT_API_RATE_LIMIT_EXCEED);
    }
}
