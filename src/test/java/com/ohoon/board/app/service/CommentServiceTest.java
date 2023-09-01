package com.ohoon.board.app.service;

import com.ohoon.board.app.dto.*;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
class CommentServiceTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    CommentService commentService;

    @Autowired
    MemberService memberService;

    @Autowired
    PostService postService;

    MemberProfileDto memberProfileDto;

    PostReadDto postReadDto;

    @BeforeEach
    void initMember() {
        Long memberId = memberService.join(
                new MemberJoinDto("testUser", "1234", "ohoon", "0hoon5661@gmail.com"));
        memberProfileDto = memberService.findById(memberId);

        Long postId = postService.write(memberId, new PostWriteDto("hello", "nice to meet you!"));
        postReadDto = postService.read(postId);
    }

    @DisplayName("댓글 작성")
    @Test
    void write() {
        Page<CommentListDto> before = commentService.listByPostId(postReadDto.getPostId(), PageRequest.of(0, 30));

        commentService.write(
                memberProfileDto.getMemberId(),
                postReadDto.getPostId(),
                new CommentWriteDto("first comment"));

        Page<CommentListDto> after = commentService.listByPostId(postReadDto.getPostId(), PageRequest.of(0, 30));

        assertThat(after.getTotalElements() - before.getTotalElements()).isEqualTo(1);
    }

    @DisplayName("댓글 삭제")
    @Test
    void remove() {
        Long commentId = commentService.write(
                memberProfileDto.getMemberId(),
                postReadDto.getPostId(),
                new CommentWriteDto("it is spam message"));

        Page<CommentListDto> before = commentService.listByPostId(postReadDto.getPostId(), PageRequest.of(0, 30));

        commentService.remove(commentId);

        Page<CommentListDto> after = commentService.listByPostId(postReadDto.getPostId(), PageRequest.of(0, 30));

        assertThat(after.getTotalElements() - before.getTotalElements()).isEqualTo(-1);
    }
}