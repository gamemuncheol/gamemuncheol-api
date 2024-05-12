package com.gamemoonchul.domain.entity;

import com.gamemoonchul.domain.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "POST")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @Setter
    @JoinColumn(name = "memberId", referencedColumnName = "id")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private List<VoteOptions> voteOptions;

    private String videoUrl;

    private String thumbnailUrl;

    private String title;

    private String content;
    @ColumnDefault("0L") @Builder.Default
    private Long viewCount = 0L;
    @ColumnDefault("0L") @Builder.Default
    private Long commentCount = 0L;
    @ColumnDefault("0L") @Builder.Default
    private Long voteCount = 0L;

    public void addVoteOptions(List<VoteOptions> voteOptions) {
        if(this.voteOptions == null) {
            this.voteOptions = new ArrayList<VoteOptions>();
        }
        this.voteOptions.addAll(voteOptions);
    }
}
