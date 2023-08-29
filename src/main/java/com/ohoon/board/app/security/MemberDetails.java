package com.ohoon.board.app.security;

import com.ohoon.board.app.dto.CurrentMemberDto;
import com.ohoon.board.domain.AuthPassword;
import com.ohoon.board.domain.Member;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDetails implements UserDetails {

    private final Member member;

    private final AuthPassword authPassword;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        member.getRoles().forEach(role -> collectors.add(role::getFullType));
        return collectors;
    }

    @Override
    public String getPassword() {
        return authPassword.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static MemberDetails create(Member member, AuthPassword authPassword) {
        return new MemberDetails(member, authPassword);
    }

    public CurrentMemberDto getCurrentMember() {
        return CurrentMemberDto.create(member);
    }
}
