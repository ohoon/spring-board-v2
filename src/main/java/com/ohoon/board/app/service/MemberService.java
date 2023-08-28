package com.ohoon.board.app.service;

import com.ohoon.board.app.dto.MemberJoinDto;
import com.ohoon.board.app.dto.MemberProfileDto;
import com.ohoon.board.app.exception.MemberNotFoundException;
import com.ohoon.board.app.repository.AuthPasswordRepository;
import com.ohoon.board.app.repository.MemberRepository;
import com.ohoon.board.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final AuthPasswordRepository authPasswordRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(MemberJoinDto memberJoinDto) {
        Member savedMember = memberRepository.save(memberJoinDto.toMemberEntity());
        memberJoinDto.setPassword(passwordEncoder.encode(memberJoinDto.getPassword()));
        authPasswordRepository.save(memberJoinDto.toAuthPasswordEntity(savedMember));
        return savedMember.getId();
    }

    public MemberProfileDto findById(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("해당 회원이 존재하지 않습니다."));
        return MemberProfileDto.fromEntity(findMember);
    }
}
