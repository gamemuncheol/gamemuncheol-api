package com.gamemoonchul.domain.entity.riot;

import com.gamemoonchul.domain.model.vo.riot.ParticipantRecord;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "match_user")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchUser {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "game_id")
    private MatchGame matchGame;
    private String puuid;
    /**
     * summonerId + riotIdTagline
     */
    private String nickname;
    @Column(name = "champion_name")
    private String championName;
    private boolean win;

    public MatchUser(ParticipantRecord participant, MatchGame matchGame) {
        this.puuid = participant.puuid();
        this.win = participant.win();
        this.nickname = getNickname(participant);
        this.championName = participant.championName();
        setMatchGame(matchGame);
    }

    public void setMatchGame(MatchGame matchGame) {
        if (this.matchGame != null) {
            this.matchGame.getMatchUsers()
                .remove(this);
        }
        this.matchGame = matchGame;
        matchGame.addMatchUser(this);
    }

    private String getNickname(ParticipantRecord participantVO) {
        return participantVO.summonerName() + " #" + participantVO.riotIdTagline();
    }
}
