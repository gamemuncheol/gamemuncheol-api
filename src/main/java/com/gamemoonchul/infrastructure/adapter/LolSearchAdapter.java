package com.gamemoonchul.infrastructure.adapter;

import com.gamemoonchul.common.exception.ApiException;
import com.gamemoonchul.domain.model.vo.riot.AccountRecord;
import com.gamemoonchul.domain.model.vo.riot.MatchRecord;
import com.gamemoonchul.domain.status.SearchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LolSearchAdapter {
    @Value("${lol.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate;

    @Autowired
    LolSearchAdapter(RestTemplate restTemplate) {
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
     public MatchRecord searchMatch(String matchId) {
         try {
             matchId = "KR_" + matchId; // "KR_" + "6862565824
             MatchRecord result =  restTemplate.getForObject(
                     "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchId + "?api_key=" + apiKey,
                     MatchRecord.class
             );
             return result;
         } catch (Exception e) {
             throw new ApiException(SearchStatus.SEARCH_RESULT_NOT_FOUND);
         }
    }
}