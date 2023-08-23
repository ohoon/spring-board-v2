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
                new MemberJoinDto("testUser", "ohoon", "0hoon5661@gmail.com"));
        memberProfileDto = memberService.findById(memberId);

        Long postId = postService.write(memberId, new PostWriteDto("hello", "nice to meet you!"));
        postReadDto = postService.read(postId);
    }

    @DisplayName("댓글 작성")
    @Test
    void write() {
        commentService.write(
                memberProfileDto.getMemberId(),
                postReadDto.getPostId(),
                new CommentWriteDto("first comment"));

        Page<CommentListDto> commentListDtoPage = commentService.list(PageRequest.of(0, 30));

        assertThat(commentListDtoPage.getTotalElements()).isEqualTo(1);
        assertThat(commentListDtoPage.getContent().get(0).getContent()).isEqualTo("first comment");
    }

    @DisplayName("댓글 리스트")
    @Test
    void list() {
        commentService.write(
                memberProfileDto.getMemberId(),
                postReadDto.getPostId(),
                new CommentWriteDto("first comment"));
        commentService.write(
                memberProfileDto.getMemberId(),
                postReadDto.getPostId(),
                new CommentWriteDto("second comment"));
        commentService.write(
                memberProfileDto.getMemberId(),
                postReadDto.getPostId(),
                new CommentWriteDto("three comment"));


        Page<CommentListDto> commentListDtoPage = commentService.list(PageRequest.of(0, 30));

        assertThat(commentListDtoPage.getTotalElements()).isEqualTo(3);
        assertThat(commentListDtoPage)
                .extracting("content")
                .containsExactly("first comment", "second comment", "three comment");
    }

    @DisplayName("댓글 수정")
    @Test
    void edit() {
        Long commentId = commentService.write(
                memberProfileDto.getMemberId(),
                postReadDto.getPostId(),
                new CommentWriteDto("hi~"));

        CommentListDto commentBeforeEdit = commentService.list(PageRequest.of(0, 30)).getContent().get(0);
        commentService.edit(commentId, new CommentEditDto("i edited comment"));
        entityManager.flush();
        entityManager.clear();
        CommentListDto commentAfterEdit = commentService.list(PageRequest.of(0, 30)).getContent().get(0);

        assertThat(commentBeforeEdit).isNotEqualTo(commentAfterEdit);
    }

    @DisplayName("댓글 삭제")
    @Test
    void remove() {
        Long commentId = commentService.write(
                memberProfileDto.getMemberId(),
                postReadDto.getPostId(),
                new CommentWriteDto("it is spam message"));

        commentService.remove(commentId);
        Page<CommentListDto> commentListDtoPage = commentService.list(PageRequest.of(0, 30));

        assertThat(commentListDtoPage.getContent()).isEmpty();
    }
}