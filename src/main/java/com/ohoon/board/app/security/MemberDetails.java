package com.ohoon.board.app.security;

import com.ohoon.board.app.dto.CurrentMemberDto;
import com.ohoon.board.domain.AuthPassword;
import com.ohoon.board.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class MemberDetails implements UserDetails {

    private final Member member;

    private final AuthPassword authPassword;

    private final Collection<GrantedAuthority> authorities = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
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
        return !member.isQuited();
    }

    private MemberDetails(Member member, AuthPassword authPassword) {
        this.member = member;
        this.authPassword = authPassword;
    }

    public static MemberDetails create(Member member, AuthPassword authPassword) {
        MemberDetails memberDetails = new MemberDetails(member, authPassword);
        member.getRoles().forEach(role -> memberDetails.authorities.add(role::getFullType));
        return memberDetails;
    }

    public CurrentMemberDto getCurrentMember() {
        return CurrentMemberDto.create(member);
    }

    public boolean isMember() {
        return this.authorities.contains(new SimpleGrantedAuthority("ROLE_MEMBER"));
    }

    public boolean isAdmin() {
        return this.authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
