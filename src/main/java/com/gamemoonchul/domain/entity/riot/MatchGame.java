package com.gamemoonchul.domain.entity.riot;

import com.gamemoonchul.domain.model.vo.riot.MatchRecord;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Entity
@Table(name = "match_game", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"game_id"})
})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchGame {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "game_id", nullable = false)
    private String gameId;

    @Column(name = "game_creation")
    private String gameCreation;

    @Column(name = "game_duration")
    private long gameDuration;

    @Column(name = "game_mode")
    private String gameMode;

    @OneToMany(mappedBy = "matchGame", fetch = FetchType.EAGER)
    private List<MatchUser> matchUsers;

    public MatchGame(MatchRecord record) {
        this.gameId = record.metadata().matchId();
        this.gameDuration = record.info().gameDuration();
        this.gameMode = record.info().gameMode();
        this.gameCreation = convertUnixToUtcTime(record.info().gameCreation());
    }

    public void addMatchUser(MatchUser matchUser) {
        if (matchUsers == null) {
            matchUsers = new ArrayList<>();
        }
        matchUsers.add(matchUser);
    }

    private String convertUnixToUtcTime(long unixTimestamp) {
        Instant instant = Instant.ofEpochMilli(unixTimestamp);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneId.of("UTC"));
        String utcTime = formatter.format(instant);
        return utcTime;
    }

}
