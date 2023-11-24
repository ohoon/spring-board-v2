package com.ohoon.board.app.repository;

import com.ohoon.board.app.dto.PostSearchCondition;
import com.ohoon.board.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostQueryRepository {

    Page<Post> list(PostSearchCondition condition, Pageable pageable);

    Page<Post> listOfBest(PostSearchCondition condition, Pageable pageable);
}
