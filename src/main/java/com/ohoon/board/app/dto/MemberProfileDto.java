package com.ohoon.board.app.dto;

import com.ohoon.board.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberProfileDto {

    private Long memberId;

    private String username;

    private String nickname;

    private String email;

    private boolean isSocial;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    public static MemberProfileDto fromEntity(Member member, boolean isSocial) {
        return new MemberProfileDto(
                member.getId(),
                member.getUsername(),
                member.getNickname(),
                member.getEmail(),
                isSocial,
                member.getCreatedDate(),
                member.getLastModifiedDate());
    }
}
