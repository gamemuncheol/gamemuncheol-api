package com.gamemoonchul.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "POST")
@Getter @SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class Post extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @Setter
    @JoinColumn(name = "memberId", referencedColumnName = "id")
    private Member member;

    private String videoUrl;

    private String thumbnailUrl;

    private String title;

    private String content;
    @ColumnDefault("0")
    private Long viewCount;
    @ColumnDefault("0")
    private Long commentCount;
    @ColumnDefault("0")
    private Long voteCount;
}
