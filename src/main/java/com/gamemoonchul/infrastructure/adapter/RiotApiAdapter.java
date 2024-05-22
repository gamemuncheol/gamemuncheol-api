package com.gamemoonchul.infrastructure.adapter;

import com.gamemoonchul.application.ports.output.RiotApiPort;
import com.gamemoonchul.common.exception.BadRequestException;
import com.gamemoonchul.domain.model.vo.riot.AccountRecord;
import com.gamemoonchul.domain.model.vo.riot.MatchRecord;
import com.gamemoonchul.domain.status.SearchStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RiotApiAdapter implements RiotApiPort {
    @Value("${lol.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate;

    @Autowired
    RiotApiAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AccountRecord searchUser(String gameName, String tagLine) {
        AccountRecord result =  restTemplate.getForObject(
                "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + gameName + "/" + tagLine + "?api_key=" + apiKey,
                AccountRecord.class
        );

        return result;
    }

    /**
     * User의 게임 목록을 가져옴
     * @param matchId
     * @return
     */
    @Override
     public MatchRecord searchMatch(String matchId) {
         try {
             MatchRecord result =  restTemplate.getForObject(
                     "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchId + "?api_key=" + apiKey,
                     MatchRecord.class
             );
             return result;
         } catch (Exception e) {
             throw new BadRequestException(SearchStatus.SEARCH_RESULT_NOT_FOUND);
         }
    }
}
