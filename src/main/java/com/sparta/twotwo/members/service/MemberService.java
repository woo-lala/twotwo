package com.sparta.twotwo.members.service;

import com.sparta.twotwo.members.dto.SignupRequestDto;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;


    public void createMember(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();
        String username = requestDto.getUsername();
//        String password = passwordEncoder.encode(requestDto.getPassword());
        String password = requestDto.getPassword();

        existEmail(email);
        existUsername(username);

        Member member = new Member(email, nickname, username, password);

        memberRepository.save(member);
    }

    public void existEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isPresent()) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }
    }

    public void existUsername(String username) {
        Optional<Member> member = memberRepository.findByUsername(username);

        if (member.isPresent()) {
            throw new IllegalArgumentException("중복된 이름입니다.");
        }
    }
}
