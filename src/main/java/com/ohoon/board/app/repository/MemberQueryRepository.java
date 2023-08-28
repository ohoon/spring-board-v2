package com.ohoon.board.app.repository;

import com.ohoon.board.domain.Member;

import java.util.Optional;

public interface MemberQueryRepository {

    Optional<Member> findByUsername(String username);
}
