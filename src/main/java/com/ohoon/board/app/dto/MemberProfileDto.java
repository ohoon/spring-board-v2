package com.ohoon.board.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberProfileDto {

    private Long memberId;

    private String username;

    private String nickname;

    private String email;

    private boolean isSocial;

    private List<String> roles = new ArrayList<>();

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    public MemberProfileDto(Long memberId, String username, String nickname, String email, boolean isSocial, List<String> roles, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.memberId = memberId;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.isSocial = isSocial;
        this.roles.addAll(roles);
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
