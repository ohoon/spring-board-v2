package com.ohoon.board.app.dto;

import com.ohoon.board.domain.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentMemberDto {

    private Long memberId;

    private String memberName;

    private List<RoleType> roles;

    public boolean isAdmin() {
        return roles.contains(RoleType.ADMIN);
    }
}
