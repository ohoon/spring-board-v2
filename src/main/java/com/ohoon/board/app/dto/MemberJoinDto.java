package com.ohoon.board.app.dto;

import com.ohoon.board.domain.AuthPassword;
import com.ohoon.board.domain.Member;
import com.ohoon.board.domain.Role;
import com.ohoon.board.domain.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinDto {

    private String username;

    private String password;

    private String nickname;

    private String email;

    public Member toMemberEntity() {
        return Member.create(
                this.username,
                this.nickname,
                this.email,
                Role.create(RoleType.MEMBER));
    }

    public AuthPassword toAuthPasswordEntity(Member member) {
        return AuthPassword.create(this.password, member);
    }
}
