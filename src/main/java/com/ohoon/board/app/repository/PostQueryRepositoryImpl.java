package com.ohoon.board.app.repository;

import com.ohoon.board.domain.Post;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ohoon.board.domain.QMember.*;
import static com.ohoon.board.domain.QPost.*;

@Repository
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Post> list(Pageable pageable) {
        List<Post> content = queryFactory
                .selectFrom(post)
                .leftJoin(post.member, member).fetchJoin()
                .where(post.isRemoved.isFalse())
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .where(post.isRemoved.isFalse());

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
