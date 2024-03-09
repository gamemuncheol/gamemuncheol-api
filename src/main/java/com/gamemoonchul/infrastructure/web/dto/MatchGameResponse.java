package com.gamemoonchul.infrastructure.web.dto;

import com.gamemoonchul.domain.entity.riot.MatchGame;
import com.gamemoonchul.domain.entity.riot.MatchUser;
import com.google.api.client.util.DateTime;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MatchGameResponse {
    public DateTime gameCreation;
    private long gameDuration;
    private String gameMode;
    private List<MatchUserResponse> matchUsers;

    public static MatchGameResponse toResponse(MatchGame matchGame) {
        return MatchGameResponse.builder()
                .gameCreation(matchGame.getGameCreation())
                .gameDuration(matchGame.getGameDuration())
                .gameMode(matchGame.getGameMode())
                .matchUsers(matchGame.getMatchUsers().stream()
                        .map(MatchUserResponse::toResponse)
                        .toList())
                .build();
    }

    @Getter
    @Builder
    static class MatchUserResponse {
        private String puuid;
        private String nickname;
        private String championThumbnail;
        private boolean win;

        public static MatchUserResponse toResponse(MatchUser matchUser) {
            return MatchUserResponse.builder()
                    .puuid(matchUser.getPuuid())
                    .nickname(matchUser.getNickname())
                    .championThumbnail(matchUser.getChampionThumbnail())
                    .win(matchUser.isWin())
                    .build();
        }
    }
}
