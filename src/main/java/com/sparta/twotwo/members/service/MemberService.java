package com.sparta.twotwo.members.service;

import com.sparta.twotwo.members.dto.SignupRequestDto;
import com.sparta.twotwo.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void createMember(SignupRequestDto requestDto) {


    }
}
