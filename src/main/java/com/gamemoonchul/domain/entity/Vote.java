package com.gamemoonchul.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder @Getter
@AllArgsConstructor
@Entity(name = "VOTE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "optionsId")
    private VoteOptions voteOptions;

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "memberId")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "postId")
    private Post post;
}
