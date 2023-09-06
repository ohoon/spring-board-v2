package com.ohoon.board.app.security;

import com.ohoon.board.app.repository.AuthPasswordRepository;
import com.ohoon.board.app.repository.MemberRepository;
import com.ohoon.board.domain.AuthPassword;
import com.ohoon.board.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final AuthPasswordRepository authPasswordRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디가 존재하지 않습니다."));
        AuthPassword findAuthPassword = authPasswordRepository.findByMember(findMember)
                .orElseThrow(() -> new AuthenticationServiceException("해당 회원의 인증 정보가 존재하지 않습니다."));
        return MemberDetails.create(findMember, findAuthPassword);
    }
}
