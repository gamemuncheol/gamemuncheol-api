package com.gamemoonchul.domain.entity.riot;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Entity
@Table(name = "MATCH_GAME", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"gameId"})
})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchGame {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "gameId", nullable = false)
    private String gameId;

    private String gameCreation;

    private long gameDuration;

    private String gameMode;

    @OneToMany(mappedBy = "matchGame", fetch = FetchType.EAGER)
    private List<MatchUser> matchUsers;

    public void addMatchUser(MatchUser matchUser) {
        if (matchUsers == null) {
            matchUsers = new ArrayList<>();
        }
        matchUsers.add(matchUser);
    }
}
