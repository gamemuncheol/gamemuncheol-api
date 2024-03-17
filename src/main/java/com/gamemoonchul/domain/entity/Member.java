package com.gamemoonchul.domain.entity;

import com.gamemoonchul.config.oauth.user.OAuth2Provider;
import com.gamemoonchul.domain.enums.MemberRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "MEMBER", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"identifier"})
})
@SuperBuilder
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

    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(nullable = false, length = 50)
    private String email;

    private String picture;

    @Column(nullable = false)
    private Double score;

    private LocalDateTime birth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private MemberRole role;

    public Member update(String name, String nickname) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}