package com.gamemoonchul.domain.entity;

import com.gamemoonchul.domain.entity.riot.MatchUser;
import jakarta.persistence.*;
import lombok.*;


@Builder
@Getter
@Entity(name = "VOTE_OPTIONS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class VoteOptions {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "matchUserId")
    private MatchUser matchUser;
}
