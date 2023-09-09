package com.ohoon.board.app.security;

import com.ohoon.board.app.dto.CurrentMemberDto;
import com.ohoon.board.domain.Member;
import com.ohoon.board.domain.AuthSocial;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class OAuth2Member implements OAuth2User {

    private final Member member;

    private final AuthSocial authSocial;

    private final Map<String, Object> attributes;

    private final Collection<GrantedAuthority> authorities = new ArrayList<>();

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getName() {
        return this.authSocial.getSubject();
    }

    private OAuth2Member(Member member, AuthSocial authSocial, Map<String, Object> attributes) {
        this.member = member;
        this.authSocial = authSocial;
        this.attributes = attributes;
    }

    public static OAuth2Member create(Member member, AuthSocial authSocial, Map<String, Object> attributes) {
        OAuth2Member oAuth2Member = new OAuth2Member(member, authSocial, attributes);
        member.getRoles().forEach(role -> oAuth2Member.authorities.add(role::getFullType));
        return oAuth2Member;
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
