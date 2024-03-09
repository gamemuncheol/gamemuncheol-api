package com.gamemoonchul.domain.entity.riot;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "MATCH_USER")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchUser {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "gameId")
    private MatchGame matchGame;
    private String puuid;
    /**
     * summonerId + riotIdTagline
     */
    private String nickname;
    private String championThumbnail;
    private boolean win;
    public void setMatchGame(MatchGame matchGame) {
        if(this.matchGame != null) {
            this.matchGame.getMatchUsers().remove(this);
        }
        this.matchGame = matchGame;
        matchGame.getMatchUsers().add(this);
    }

    public static class Dummy {
        public static MatchUser createDummy(String puuid) {
            return MatchUser.builder()
                    .puuid(puuid)
                    .nickname(puuid)
                    .championThumbnail(puuid)
                    .win(true)
                    .build();
        }
    }
}
