package com.ohoon.board.app.repository;

import com.ohoon.board.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentQueryRepository {

    @Query("SELECT count(c) FROM Comment c WHERE c.post.id = :id AND c.isRemoved = false")
    long countByPostId(@Param("id") Long postId);

    List<Comment> findFirst6ByOrderByIdDesc();
}
