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
class PostServiceTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    PostService postService;

    @Autowired
    MemberService memberService;

    MemberProfileDto memberProfileDto;

    @BeforeEach
    void initMember() {
        Long memberId = memberService.join(
                new MemberJoinDto("testUser", "1234", "ohoon", "0hoon5661@gmail.com"));
        memberProfileDto = memberService.findById(memberId);
    }

    @DisplayName("글 작성")
    @Test
    void write() {
        Long postId = postService.write(
                memberProfileDto.getMemberId(),
                new PostWriteDto("post write test", "hello world!"));

        PostReadDto postReadDto = postService.read(postId);

        assertThat(postReadDto.getTitle()).isEqualTo("post write test");
        assertThat(postReadDto.getContent()).isEqualTo("hello world!");
    }

    @DisplayName("글 리스트")
    @Test
    void list() {
        Page<PostListDto> before = postService.list(PageRequest.of(0, 30));

        postService.write(
                memberProfileDto.getMemberId(),
                new PostWriteDto("post write test", "hello world!"));
        postService.write(
                memberProfileDto.getMemberId(),
                new PostWriteDto("good morning", "nice day"));
        postService.write(
                memberProfileDto.getMemberId(),
                new PostWriteDto("help me", ":("));

        Page<PostListDto> after = postService.list(PageRequest.of(0, 30));

        assertThat(after.getTotalElements() - before.getTotalElements()).isEqualTo(3);
    }

    @DisplayName("글 수정")
    @Test
    void edit() {
        Long postId = postService.write(
                memberProfileDto.getMemberId(),
                new PostWriteDto("post write test", "hello world!"));

        PostReadDto postBeforeEdit = postService.read(postId);
        postService.edit(postId, new PostEditDto("edit title", "(^_^)"));
        entityManager.flush();
        entityManager.clear();
        PostReadDto postAfterEdit = postService.read(postId);

        assertThat(postBeforeEdit).isNotEqualTo(postAfterEdit);
    }

    @DisplayName("글 삭제")
    @Test
    void remove() {
        Long postId = postService.write(
                memberProfileDto.getMemberId(),
                new PostWriteDto("post write test", "hello world!"));

        Page<PostListDto> before = postService.list(PageRequest.of(0, 30));

        postService.remove(postId);

        Page<PostListDto> after = postService.list(PageRequest.of(0, 30));

        assertThat(after.getTotalElements() - before.getTotalElements()).isEqualTo(-1);
    }
}