package com.ohoon.board.app.repository;

import com.ohoon.board.domain.AuthSocial;
import com.ohoon.board.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthSocialRepository extends JpaRepository<AuthSocial, Long> {

    Optional<AuthSocial> findBySubject(String subject);

    boolean existsBySubject(String subject);

    boolean existsByMember(Member member);
}
