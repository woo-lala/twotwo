package com.sparta.twotwo.members.service;

import com.sparta.twotwo.auth.util.AuthorityUtil;
import com.sparta.twotwo.common.exception.ErrorCode;
import com.sparta.twotwo.common.exception.TwotwoApplicationException;
import com.sparta.twotwo.members.dto.MemberResponseDto;
import com.sparta.twotwo.members.dto.SignupRequestDto;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthorityUtil authorityUtil;
    private final PasswordEncoder passwordEncoder;


    public void createMember(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        boolean is_public = requestDto.is_public();
        List<String> roles = authorityUtil.createRoles(email);

        existEmail(email);
        existUsername(username);

        Member member = new Member(username, nickname, email, password, roles, is_public);

        memberRepository.save(member);
    }

    public List<Member> getMembers() {

        return memberRepository.findAll();
    }

    public Member getMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        return optionalMember.orElseThrow(() ->
             new TwotwoApplicationException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public void existEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isPresent()) {
            throw new TwotwoApplicationException(ErrorCode.MEMBER_EMAIL_EXIST);
        }
    }

    public void existUsername(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);

        if (member.isPresent()) {
            throw new TwotwoApplicationException(ErrorCode.MEMBER_USERNAME_EXIST);
        }
    }


}
