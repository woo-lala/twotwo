package com.sparta.twotwo.auth.service;

import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.entity.MemberStatusEnum;
import com.sparta.twotwo.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByUsername(username);
        Member findMember = member.orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        if (findMember.getMemberStatus() == MemberStatusEnum.DELETE) {
            throw new TwotwoApplicationException(ErrorCode.MEMBER_NOT_FOUND);
        }

        System.out.println(username);
        System.out.println(member);
        System.out.println(findMember.getPassword());

        return new MemberDetails(findMember);
    }
}
