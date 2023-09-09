package com.ohoon.board.web;

import com.ohoon.board.app.dto.CommentWriteDto;
import com.ohoon.board.app.dto.MemberJoinDto;
import com.ohoon.board.app.dto.PostWriteDto;
import com.ohoon.board.app.service.CommentService;
import com.ohoon.board.app.service.MemberService;
import com.ohoon.board.app.service.PostService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
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
            for (int i = 1; i <= 100; i++) {
                Long post = postService.write(member1, new PostWriteDto(
                        "글 작성 테스트 " + i,
                        "테스트 번호: " + i));
                for (int j = 1; j <= 50; j++) {
                    commentService.write(member2, post,
                            new CommentWriteDto("댓글 테스트 " + j));
                }
            }
        }
    }
}
