package com.ohoon.board.app.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSearchCondition {

    @Enumerated(EnumType.STRING)
    private MemberSearchMode mode = MemberSearchMode.USERNAME;

    private String keyword;
}
