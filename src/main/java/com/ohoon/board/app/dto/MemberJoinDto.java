package com.ohoon.board.app.dto;

import com.ohoon.board.domain.AuthPassword;
import com.ohoon.board.domain.Member;
import com.ohoon.board.domain.Role;
import com.ohoon.board.domain.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinDto {

    @NotEmpty(message = "아이디를 입력해주세요.")
    @Size(min = 5, max = 20,
            message = "아이디는 5글자 ~ 20글자 이어야 합니다.")
    private String username;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20,
            message = "비밀번호는 8글자 ~ 20글자 이어야 합니다.")
    private String password;

    @Size(min = 2, max = 12,
            message = "닉네임은 2글자 ~ 12글자 이어야 합니다.")
    private String nickname;

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
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
