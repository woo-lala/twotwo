package com.sparta.twotwo.members.controller;

import com.sparta.twotwo.members.dto.SignupRequestDto;
import com.sparta.twotwo.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Void> createMember(@RequestBody SignupRequestDto requestDto) {
        memberService.createMember(requestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
