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

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "optionsId")
    private VoteOptions voteOptions;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "postId")
    private Post post;
}
