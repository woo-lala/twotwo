package com.sparta.twotwo.auth.service;

import com.sparta.twotwo.auth.util.AuthorityUtil;
import com.sparta.twotwo.members.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MemberDetails implements UserDetails {

    private final AuthorityUtil authorityUtil;
    @Getter
    private final Member member;

    public MemberDetails(Member member) {
       this.member = member;
       authorityUtil = new AuthorityUtil();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityUtil.createAuthorities(member.getRoles());
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
