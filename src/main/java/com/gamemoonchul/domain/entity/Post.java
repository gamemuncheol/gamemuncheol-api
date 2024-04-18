package com.gamemoonchul.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "POST")
@Getter @SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class Post extends BaseTimeEntity {
    @ManyToOne(fetch = FetchType.LAZY) @Setter
    @JoinColumn(name = "memberId", referencedColumnName = "id")
    private Member member;

    private String videoUrl;

    private String thumbnailUrl;

    private String title;

    private String content;

    private Long viewCount;
}
