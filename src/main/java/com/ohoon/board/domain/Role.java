package com.ohoon.board.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Getter
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private RoleType type;

    private Role(RoleType type) {
        this.type = type;
    }

    public static Role create(RoleType type) {
        return new Role(type);
    }

    public String getFullType() {
        return type.toFullName();
    }

    public void assignMember(Member member) {
        this.member = member;
    }
}
