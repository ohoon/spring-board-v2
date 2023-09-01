package com.ohoon.board.app.repository;

import com.ohoon.board.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentQueryRepository {

    Page<Comment> listByPostId(Long postId, Pageable pageable);
}
