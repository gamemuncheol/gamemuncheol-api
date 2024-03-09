package com.gamemoonchul.domain.model.vo.riot;

import lombok.*;

import java.util.List;

@Getter @Builder
@AllArgsConstructor
public class InfoVO {
    /**
     * 게임 생성된 시간
     */
    private final long gameCreation;

    /**
     * 게임 시간
     */
    private final long gameDuration;

    /**
     * 게임 종료된 시간
     */
    private long gameEndTimestamp;

    /**
     * 게임 ID
     */
    private final long gameId;

    /**
     * 게임 모드
     */
    private final String gameMode;

    private final String gameName;

    private final long gameStartTimestamp;

    private final String gameType;

    private final String gameVersion;

    private final int mapId;

    private final List<ParticipantVO> participants;

    private final List<TeamVO> teams;

    public static class Dummy {
        public static InfoVO createDummy() {
            return InfoVO.builder()
                    .gameCreation(1)
                    .gameDuration(1)
                    .gameEndTimestamp(1)
                    .gameId(1)
                    .gameMode("1")
                    .gameName("1")
                    .gameStartTimestamp(1)
                    .gameType("1")
                    .gameVersion("1")
                    .mapId(1)
                    .participants(List.of(ParticipantVO.Dummy.createDummy()))
                    .teams(List.of(TeamVO.Dummy.createDummy()))
                    .build();
        }
    }
}
