package com.sparta.twotwo.members.controller;

import com.sparta.twotwo.common.response.ApiResponse;
import com.sparta.twotwo.members.dto.MemberRequestDto;
import com.sparta.twotwo.members.dto.MemberResponseDto;
import com.sparta.twotwo.members.dto.SignupRequestDto;
import com.sparta.twotwo.members.entity.Member;
import com.sparta.twotwo.members.entity.RolesEnum;
import com.sparta.twotwo.members.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<Page<MemberResponseDto>>> gerMembers(@RequestParam(defaultValue = "10") int size,
                                                                           @RequestParam(defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Member> members = memberService.getMembers(pageable);

        Page<MemberResponseDto> responseDto =  members.map(MemberResponseDto::new);

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

    @PatchMapping("/grant/owner/{member_id}")
    public ResponseEntity<ApiResponse<MemberResponseDto>> updateMemberAuth(@PathVariable("member_id") Long member_id) {
        Member member = memberService.addMemberAuth(member_id, RolesEnum.OWNER);
        MemberResponseDto response = new MemberResponseDto(member);

        return new ResponseEntity<>(ApiResponse.success(response), HttpStatus.OK);
    }


    @DeleteMapping("/{member_id}")
    public void deleteMember(@PathVariable("member_id") Long member_id) {

        memberService.deleteMember(member_id);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<MemberResponseDto>> searchMember() {

        return null;
    }
}
