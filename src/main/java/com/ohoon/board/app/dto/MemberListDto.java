package com.ohoon.board.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberListDto {

    private Long memberId;

    private String username;

    private String nickname;

    private String email;

    private String roles;
}
