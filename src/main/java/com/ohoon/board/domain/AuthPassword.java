package com.ohoon.board.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthPassword extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "auth_password_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    private String password;

    private AuthPassword(String password, Member member) {
        this.password = password;
        this.member = member;
    }

    public static AuthPassword create(String password, Member member) {
        return new AuthPassword(password, member);
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
