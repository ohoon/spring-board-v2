package com.ohoon.board.app.repository;

import com.ohoon.board.app.dto.MemberSearchCondition;
import com.ohoon.board.app.dto.MemberSearchMode;
import com.ohoon.board.app.util.NullSafeBooleanBuilder;
import com.ohoon.board.domain.Member;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ohoon.board.app.util.NullSafeBooleanBuilder.builder;
import static com.ohoon.board.domain.QMember.member;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Member> list(MemberSearchCondition condition, Pageable pageable) {
        List<Member> content = queryFactory
                .selectFrom(member)
                .where(searchFilter(condition)
                        .and(member.isQuited.isFalse()))
                .orderBy(member.username.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(member.count())
                .from(member)
                .where(searchFilter(condition)
                        .and(member.isQuited.isFalse()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanBuilder searchFilter(MemberSearchCondition condition) {
        MemberSearchMode mode = condition.getMode();
        String keyword = condition.getKeyword();
        return switch (mode) {
            case USERNAME -> usernameContains(keyword);
            case NICKNAME -> nicknameContains(keyword);
            case EMAIL -> emailContains(keyword);
        };
    }

    private BooleanBuilder usernameContains(String username) {
        return builder(() -> member.username.contains(hasText(username) ? username : null));
    }

    private BooleanBuilder nicknameContains(String nickname) {
        return builder(() -> member.nickname.contains(hasText(nickname) ? nickname : null));
    }

    private BooleanBuilder emailContains(String email) {
        return builder(() -> member.email.contains(hasText(email) ? email : null));
    }
}
