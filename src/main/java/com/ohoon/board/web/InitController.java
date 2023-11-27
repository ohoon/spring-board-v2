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

import java.util.ArrayList;
import java.util.List;

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
            memberService.joinAdmin(new MemberJoinDto("admin", "admin", "관리자", "0hoon5661@gmail.com"));

            List<Long> members = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                members.add(memberService.join(
                        new MemberJoinDto(
                                "testMember" + i,
                                "test1234",
                                "test",
                                "test@example.org")));
            }

            for (int i = 1; i <= 100; i++) {
                Long post = postService.write(members.get(i-1), new PostWriteDto(
                        "글 작성 테스트 " + i,
                        "테스트 번호: " + i));
                for (int j = 1; j <= i/2; j++) {
                    postService.vote(members.get(j-1), post);
                }

                for (int j = 1; j <= 50; j++) {
                    commentService.write(members.get(j-1), post,
                            new CommentWriteDto("댓글 테스트 " + j));
                }
            }
        }
    }
}
