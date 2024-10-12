package com.gamemoonchul.domain.entity;

import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.domain.entity.base.BaseTimeEntity;
import com.gamemoonchul.domain.entity.redis.RedisMember;
import com.gamemoonchul.domain.enums.MemberRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member", uniqueConstraints = {
    @UniqueConstraint(columnNames = "nickname"
    ), @UniqueConstraint(columnNames = {"provider", "identifier"})
})
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 30)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30, columnDefinition = "varchar(255)")
    private OAuth2Provider provider;

    @Column(nullable = false, length = 30)
    private String identifier;

    @Setter
    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(nullable = false, length = 50)
    private String email;

    private String picture;

    @Setter
    @Column(name = "privacy_agreed")
    private boolean privacyAgreed;

    @Setter
    @Column(name = "privacy_agreed_at")
    private LocalDateTime privacyAgreedAt;

    @Column(nullable = false)
    private Double score;

    private LocalDateTime birth;

    @Enumerated(EnumType.STRING)
    @Setter
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private MemberRole role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberBan> memberBans;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostBan> postBans;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes;

    public Member(RedisMember redisMember) {
        this.role = MemberRole.USER;
        this.name = redisMember.getName();
        this.identifier = redisMember.getIdentifier();
        this.provider = redisMember.getProvider();
        this.nickname = ifNicknameNotExistGetRandom(redisMember.getNickname());
        this.privacyAgreed = true;
        this.score = 0.0;
        this.email = redisMember.getEmail();
        this.picture = redisMember.getPicture();
        this.birth = null;
    }

    public Member update(String name, String nickname) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public Member updateNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    private String ifNicknameNotExistGetRandom(String currentNickname) {
        Optional<String> nickname = Optional.ofNullable(currentNickname);
        if (nickname.isEmpty()) {
            nickname = Optional.of(randomNickname());
        }
        return nickname.get();
    }

    private String randomNickname() {
        return UUID.randomUUID()
            .toString()
            .substring(0, 30);
    }
}
