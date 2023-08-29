package com.ohoon.board.web;

import com.ohoon.board.app.dto.MemberJoinDto;
import com.ohoon.board.app.service.MemberService;
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

        public void init() {
            memberService.join(
                    new MemberJoinDto(
                            "testMember",
                            "test1234",
                            "test",
                            "test@example.org"));
            memberService.join(
                    new MemberJoinDto(
                            "testMember2",
                            "test1234",
                            null,
                            "test@example.org"));
        }
    }
}
