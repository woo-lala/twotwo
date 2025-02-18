package com.sparta.twotwo.members.controller;

import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.members.dto.MemberRequestDto;
import com.sparta.twotwo.members.dto.MemberResponseDto;
import com.sparta.twotwo.members.dto.SignupRequestDto;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Void> createMember(@Valid @RequestBody SignupRequestDto requestDto) {
        memberService.createMember(requestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<MemberResponseDto>>> gerMembers() {
        List<Member> members = memberService.getMembers();
        List<MemberResponseDto> responseDto = members.stream()
                .map(MemberResponseDto::new)
                .toList();

        return new ResponseEntity<>(ApiResponse.success(responseDto), HttpStatus.OK);
    }

    @GetMapping("/{member_id}")
    public ResponseEntity<ApiResponse<MemberResponseDto>> getMember(@PathVariable("member_id") Long member_id) {
        Member member = memberService.getMember(member_id);
        MemberResponseDto response = new MemberResponseDto(member);

        return new ResponseEntity<>(ApiResponse.success(response), HttpStatus.OK);
    }

    @PatchMapping("/{member_id}")
    public ResponseEntity<ApiResponse<MemberResponseDto>> updateMember(@PathVariable("member_id") Long member_id,
                                                                       @RequestBody MemberRequestDto requestDto) {
        Member member = memberService.updateMember(member_id, requestDto);
        MemberResponseDto response = new MemberResponseDto(member);

        return new ResponseEntity<>(ApiResponse.success(response), HttpStatus.OK);
    }

    @DeleteMapping("{member_id}")
    public void deleteMember(@PathVariable("member_id") Long member_id) {

        memberService.deleteMember(member_id);
    }
}
