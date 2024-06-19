package com.gamemoonchul.domain.entity;

import com.gamemoonchul.domain.entity.riot.MatchUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Builder
@Getter
@Entity(name = "vote_options")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class VoteOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_user_id")
    private MatchUser matchUser;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_options_id")
    private List<Vote> votes;
}
