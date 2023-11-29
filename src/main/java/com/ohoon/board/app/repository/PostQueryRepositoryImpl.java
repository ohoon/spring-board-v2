package com.ohoon.board.app.repository;

import com.ohoon.board.app.dto.PostSearchCondition;
import com.ohoon.board.app.dto.PostSearchMode;
import com.ohoon.board.domain.Post;
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
import static com.ohoon.board.domain.QMember.*;
import static com.ohoon.board.domain.QPost.*;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Post> list(PostSearchCondition condition, Pageable pageable) {
        List<Post> content = queryFactory
                .selectFrom(post)
                .leftJoin(post.member, member).fetchJoin()
                .where(searchFilter(condition)
                        .and(post.isRemoved.isFalse()))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .where(searchFilter(condition)
                        .and(post.isRemoved.isFalse()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<Post> listOfBest(PostSearchCondition condition, Pageable pageable) {
        List<Post> content = queryFactory
                .selectFrom(post)
                .leftJoin(post.member, member).fetchJoin()
                .where(post.votes.size().goe(10)
                        .and(searchFilter(condition))
                        .and(post.isRemoved.isFalse()))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .where(post.votes.size().goe(10)
                        .and(searchFilter(condition))
                        .and(post.isRemoved.isFalse()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanBuilder searchFilter(PostSearchCondition condition) {
        PostSearchMode mode = condition.getMode();
        String keyword = condition.getKeyword();
        return switch (mode) {
            case TITLE_CONTENT -> titleOrContentContains(keyword);
            case TITLE -> titleContains(keyword);
            case CONTENT -> contentContains(keyword);
            case AUTHOR -> authorContains(keyword);
            case COMMENT -> commentContains(keyword);
        };
    }

    private BooleanBuilder titleOrContentContains(String titleOrContent) {
        return titleContains(titleOrContent)
                .or(contentContains(titleOrContent));
    }

    private BooleanBuilder titleContains(String title) {
        return builder(() -> post.title.contains(hasText(title) ? title : null));
    }

    private BooleanBuilder contentContains(String content) {
        return builder(() -> post.content.contains(hasText(content) ? content : null));
    }

    private BooleanBuilder authorContains(String author) {
        return builder(() -> post.author.contains(hasText(author) ? author : null));
    }

    private BooleanBuilder commentContains(String comment) {
        return builder(() -> post.comments.any().content.contains(hasText(comment) ? comment : null));
    }
}
