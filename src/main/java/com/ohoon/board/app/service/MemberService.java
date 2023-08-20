package com.ohoon.board.app.service;

import com.ohoon.board.app.dto.MemberJoinDto;
import com.ohoon.board.app.dto.MemberProfileDto;
import com.ohoon.board.app.repository.MemberRepository;
import com.ohoon.board.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(MemberJoinDto memberJoinDto) {
        Member savedMember = memberRepository.save(memberJoinDto.toEntity());
        return savedMember.getId();
    }

    public MemberProfileDto findById(Long memberId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow();
        return MemberProfileDto.fromEntity(findMember);
    }
}
