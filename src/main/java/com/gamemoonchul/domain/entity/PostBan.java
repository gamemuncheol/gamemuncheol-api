package com.gamemoonchul.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post_ban",
        uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "ban_post_id"}),
        indexes = {
                @Index(name = "idx_post_ban_member_id", columnList = "member_id"),
        }

)
public class PostBan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ban_post_id")
    private Post banPost;

    public Post getBanPost() {
        return banPost;
    }
}
