package com.ohoon.board.app.repository;

import com.ohoon.board.domain.AuthPassword;
import com.ohoon.board.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthPasswordRepository extends JpaRepository<AuthPassword, Long> {

    Optional<AuthPassword> findByMember(Member member);
}
