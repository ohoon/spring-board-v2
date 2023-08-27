package com.ohoon.board.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotNull
    @Column(updatable = false, unique = true)
    private String username;

    private String nickname;

    @NotNull
    private String email;

    private Member(String username, String nickname, String email) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
    }

    public static Member create(String username, String nickname, String email) {
        return new Member(username, nickname, email);
    }

    public String getName() {
        if (nickname == null || nickname.isBlank()) {
            return username;
        }

        return nickname;
    }
}
