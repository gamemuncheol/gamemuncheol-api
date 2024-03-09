package com.gamemoonchul.domain.entity.riot;

import com.google.api.client.util.DateTime;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Builder
@Entity(name = "MATCH_GAME")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchGame {
    @Id
    private String id;

    private DateTime gameCreation;

    private long gameDuration;

    private String gameMode;

    @OneToMany(mappedBy = "matchGame", fetch = FetchType.EAGER)
    private List<MatchUser> matchUsers;

    public void addMatchUser(MatchUser matchUser) {
        if(matchUsers == null) {
            matchUsers = new ArrayList<>();
        }
        matchUsers.add(matchUser);
    }

    public static class Dummy {
        public static MatchGame createDummy() {
            List<MatchUser> matchUsers = new ArrayList<>();
            for(int i = 0; i < 10; i++) {
                matchUsers.add(MatchUser.Dummy.createDummy("1"));
            }
            return MatchGame.builder()
                    .id("1")
                    .gameCreation(new DateTime(1))
                    .gameDuration(1)
                    .matchUsers(matchUsers)
                    .gameMode("1")
                    .build();
        }
    }
}
