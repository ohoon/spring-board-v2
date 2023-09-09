package com.ohoon.board.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthSocial extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "auth_social_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    private String subject;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProviderType provider;

    private AuthSocial(String subject, ProviderType provider, Member member) {
        this.subject = subject;
        this.provider = provider;
        this.member = member;
    }

    public static AuthSocial create(String subject, ProviderType provider, Member member) {
        return new AuthSocial(subject, provider, member);
    }

    public Long getMemberId() {
        return this.member.getId();
    }
}
