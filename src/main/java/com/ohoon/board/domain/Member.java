package com.ohoon.board.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    private boolean isQuited;

    @OneToMany(
            mappedBy = "member",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<Role> roles = new ArrayList<>();

    private Member(String username, String nickname, String email) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
    }

    public static Member create(String username, String nickname, String email, Role... roles) {
        Member member = new Member(username, nickname, email);
        for (Role role : roles) {
            member.addRole(role);
        }

        return member;
    }

    public String getName() {
        if (nickname == null || nickname.isBlank()) {
            return username;
        }

        return nickname;
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.assignMember(this);
    }

    public void modify(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }

    public void quit() {
        this.isQuited = true;
    }
}
