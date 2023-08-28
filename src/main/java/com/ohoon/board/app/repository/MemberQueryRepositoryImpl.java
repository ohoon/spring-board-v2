package com.ohoon.board.app.repository;

import com.ohoon.board.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.ohoon.board.domain.QMember.member;
import static com.ohoon.board.domain.QRole.role;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Member> findByUsername(String username) {
        Member findMember = queryFactory
                .selectFrom(member)
                .leftJoin(member.roles, role).fetchJoin()
                .where(member.username.eq(username))
                .fetchOne();

        if (findMember == null) {
            return Optional.empty();
        }

        return Optional.of(findMember);
    }
}
