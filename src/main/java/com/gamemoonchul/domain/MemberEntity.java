package com.gamemoonchul.domain;

import com.gamemoonchul.domain.enums.MemberRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "member")
@SuperBuilder
public class MemberEntity extends BaseTimeEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 12)
  private String name;

  @Column(nullable = false, length = 12)
  private String nickname;

  @Column(nullable = false, length = 30)
  private String email;

  @Setter @Column(nullable = false, length = 50)
  private String password;

  private String picture;

  private Double score;

  private LocalDateTime birth;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, columnDefinition = "varchar(255)")
  private MemberRole role;

    public MemberEntity update(String name, String nickname) {
    this.name = name;
    this.picture = picture;
    return this;
  }

  public String getRoleKey() {
    return this.role.getKey();
  }
}
