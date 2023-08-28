package com.ohoon.board.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoleType {

    MEMBER("ROLE_MEMBER"),

    ADMIN("ROLE_ADMIN");

    private final String value;

    public String toFullName() {
        return value;
    }
}
