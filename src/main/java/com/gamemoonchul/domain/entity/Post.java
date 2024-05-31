package com.gamemoonchul.domain.entity;

import com.gamemoonchul.application.converter.JsonStringListConverter;
import com.gamemoonchul.application.converter.MapLongConverter;
import com.gamemoonchul.domain.entity.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity(name = "POST")
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Setter
    @JoinColumn(name = "memberId", referencedColumnName = "id")
    private Member member;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private List<VoteOptions> voteOptions;

    @Convert(converter = MapLongConverter.class)
    private HashMap<Long, Long> votes;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private List<Comment> comments;

    private String videoUrl;

    private String thumbnailUrl;

    private String title;

    private String content;

    @Convert(converter = JsonStringListConverter.class)
    private List<String> tags;

    @Builder.Default
    private Long viewCount = 0L;
    @Builder.Default
    private Long commentCount = 0L;
    @Builder.Default
    private Long voteCount = 0L;

    private Double voteRatio;

    public void commentCountDown() {
        this.commentCount--;
    }

    public void addVoteOptions(List<VoteOptions> voteOptions) {
        if (this.voteOptions == null) {
            this.voteOptions = new ArrayList<VoteOptions>();
        }
        this.voteOptions.addAll(voteOptions);
    }

    public void addComment(Comment comment) {
        if (this.comments == null) {
            this.comments = new ArrayList<Comment>();
        }
        this.comments.add(comment);
        commentCount++;
    }
}
