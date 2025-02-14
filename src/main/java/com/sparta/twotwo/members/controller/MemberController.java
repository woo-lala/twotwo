package com.sparta.twotwo.members.controller;

import com.sparta.twotwo.members.dto.SignupRequestDto;
import com.sparta.twotwo.members.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity createMember(@RequestBody SignupRequestDto requestDto) {
        memberService.createMember(requestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
