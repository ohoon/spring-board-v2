package com.ohoon.board.app.service;

import com.ohoon.board.app.dto.MemberJoinDto;
import com.ohoon.board.app.dto.MemberProfileDto;
import com.ohoon.board.app.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberRepository repository;

    @Autowired
    MemberService service;

    @DisplayName("회원가입")
    @Test
    void join1() {
        MemberJoinDto joinDto = new MemberJoinDto("usernameA", "1234", "nicknameA", "email@example.com");
        Long memberId = service.join(joinDto);
        MemberProfileDto findMemberProfile = service.findById(memberId);
        assertThat(findMemberProfile.getUsername()).isEqualTo("usernameA");
        assertThat(findMemberProfile.getNickname()).isEqualTo("nicknameA");
    }

    @DisplayName("회원가입 (닉네임 누락)")
    @Test
    void join2() {
        MemberJoinDto joinDto = new MemberJoinDto("usernameA", "1234", null, "email@example.com");
        Long memberId = service.join(joinDto);
        MemberProfileDto findMemberProfile = service.findById(memberId);
        assertThat(findMemberProfile.getUsername()).isEqualTo("usernameA");
        assertThat(findMemberProfile.getNickname()).isNull();
    }
}