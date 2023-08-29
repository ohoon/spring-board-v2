package com.ohoon.board.app.dto;

import com.ohoon.board.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class CurrentMemberDto {

    private Long memberId;

    private String memberName;

    public static CurrentMemberDto create(Member member) {
        return new CurrentMemberDto(
                member.getId(),
                member.getName());
    }
}
