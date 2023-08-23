package com.ohoon.board.app.repository;

import com.ohoon.board.domain.Comment;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ohoon.board.domain.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Comment> list(Pageable pageable) {
        List<Comment> content = queryFactory
                .selectFrom(comment)
                .where(comment.isRemoved.isFalse())
                .orderBy(comment.id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(comment.count())
                .from(comment)
                .where(comment.isRemoved.isFalse());

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
