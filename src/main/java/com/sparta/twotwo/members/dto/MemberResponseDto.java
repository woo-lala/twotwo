package com.sparta.twotwo.members.dto;

import com.sparta.twotwo.members.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private Long member_id;
    private String username;
    private String nickname;
    private String email;
    private List<String> roles;
    private String memberStatus;

    public MemberResponseDto(Member member) {
        this.member_id = member.getMember_id();
        this.username = member.getUsername();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.roles = member.getRoles();
        this.memberStatus = member.getMemberStatus().toString();
    }
}
