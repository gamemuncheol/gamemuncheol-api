package com.gamemoonchul.domain.converter;

import com.gamemoonchul.domain.model.dto.SearchedGame;
import com.gamemoonchul.domain.redisEntity.RedisCachedGame;
import org.springframework.stereotype.Component;

@Component
public class RedisCachedGameConverter {
    public RedisCachedGame toEntity(String searchKeyword, SearchedGame searchedGame) {
        return RedisCachedGame.builder()
                .searchKeyword(searchKeyword)
                .gametype(searchedGame.getGametype())
                .gamedate(searchedGame.getGamedate())
                .gameresult(searchedGame.getGameresult())
                .gametime(searchedGame.getGametime())
                .img_src(searchedGame.getImg_src())
                .our_team(searchedGame.getOur_team())
                .enemy_team(searchedGame.getEnemy_team())
                .build();
    }
}
