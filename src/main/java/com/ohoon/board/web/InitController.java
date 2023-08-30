package com.ohoon.board.web;

import com.ohoon.board.app.dto.CommentWriteDto;
import com.ohoon.board.app.dto.MemberJoinDto;
import com.ohoon.board.app.dto.PostWriteDto;
import com.ohoon.board.app.service.CommentService;
import com.ohoon.board.app.service.MemberService;
import com.ohoon.board.app.service.PostService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequiredArgsConstructor
public class InitController {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Service
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final MemberService memberService;

        private final PostService postService;

        private final CommentService commentService;

        public void init() {
            Long member1 = memberService.join(
                    new MemberJoinDto(
                            "testMember",
                            "test1234",
                            "test",
                            "test@example.org"));
            Long member2 = memberService.join(
                    new MemberJoinDto(
                            "testMember2",
                            "test1234",
                            null,
                            "test@example.org"));
            Long post1 = postService.write(member1, new PostWriteDto(
                    "안녕하세요",
                    "반갑습니다"));
            Long post2 = postService.write(member1, new PostWriteDto(
                    "글쓰기 테스트",
                    "12345"));
            Long post3 = postService.write(member2, new PostWriteDto(
                    "스프링 부트에서 만들어진 게시판 프로젝트입니다",
                    "abcedef"));
            Long post4 = postService.write(member1, new PostWriteDto(
                    "오늘 날짜는 2023년 8월 30일",
                    "밖에는 비가 내리네요"));
            Long post5 = postService.write(member2, new PostWriteDto(
                    "abcdefghijklmnopqrstuvwxyz",
                    "**"));
            Long comment1 = commentService.write(member1, post1,
                    new CommentWriteDto("test1"));
            Long comment2 = commentService.write(member2, post1,
                    new CommentWriteDto("te33s12t2"));
            Long comment3 = commentService.write(member1, post1,
                    new CommentWriteDto("te1554st43"));
            Long comment4 = commentService.write(member2, post3,
                    new CommentWriteDto("te33st4"));
            Long comment5 = commentService.write(member1, post4,
                    new CommentWriteDto("te56st5"));
            Long comment6 = commentService.write(member2, post4,
                    new CommentWriteDto("t22e11st6"));
        }
    }
}
