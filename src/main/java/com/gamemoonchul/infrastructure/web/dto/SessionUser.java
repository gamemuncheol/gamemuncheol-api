package com.gamemoonchul.infrastructure.web.dto;

import com.gamemoonchul.domain.MemberEntity;
import lombok.Getter;

import java.io.Serializable;
import java.lang.reflect.Member;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(MemberEntity member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}
