package com.ohoon.board.app.repository;

import com.ohoon.board.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository {

    @EntityGraph(attributePaths = "roles")
    Optional<Member> findByUsername(String username);

    boolean existsByUsername(String username);
}
