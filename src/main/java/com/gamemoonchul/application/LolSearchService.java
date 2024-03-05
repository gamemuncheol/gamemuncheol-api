package com.gamemoonchul.application;

import com.gamemoonchul.domain.model.vo.riot.AccountVO;
import com.gamemoonchul.domain.model.vo.riot.MatchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LolSearchService {
    @Value("${lol.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate;

    @Autowired
    LolSearchService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AccountVO searchUser(String gameName, String tagLine) {
        AccountVO result =  restTemplate.getForObject(
                "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/" + gameName + "/" + tagLine + "?api_key=" + apiKey,
                AccountVO.class
        );

        return result;
    }

    /**
     * User의 게임 목록을 가져옴
     * @param matchId
     * @return
     */
     public MatchVO searchMatch(String matchId) {
        MatchVO result =  restTemplate.getForObject(
                "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchId + "?api_key=" + apiKey,
                MatchVO.class
        );

        return result;
    }
}
