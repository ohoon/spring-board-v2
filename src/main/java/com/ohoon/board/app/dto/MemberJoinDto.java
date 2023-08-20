package com.ohoon.board.app.dto;

import com.ohoon.board.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinDto {

    private String username;

    private String nickname;

    private String email;

    public Member toEntity() {
        return Member.create(this.username, this.nickname, this.email);
    }
}
