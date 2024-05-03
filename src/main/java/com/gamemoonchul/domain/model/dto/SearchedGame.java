package com.gamemoonchul.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchedGame {
     private String gametype;
    private String gamedate;
    private String gameresult;
    private String gametime;
    private String img_src;
    private List<SearchedUser> our_team;
    private List<SearchedUser> enemy_team;
}