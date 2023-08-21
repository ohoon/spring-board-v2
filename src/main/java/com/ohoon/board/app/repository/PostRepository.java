package com.ohoon.board.app.repository;

import com.ohoon.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryRepository {
}
